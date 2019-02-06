package fr.eisbm.EpiGeNet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import fr.eisbm.EpiGeNet.App.LabelTypes;
import fr.eisbm.EpiGeNet.App.RelTypes;

public class ColonCancerDB {

	private final String EVENT_CONDITIONAL_PROBABILITIES = "files/cond_events_26112015.txt";
	private final String EVENT_SIMPLE_PROBABILITIES = "files/simple_events_26112015.txt";

	Map<EventInfo_s, Node> molecNodeMap = new HashMap<EventInfo_s, Node>();
	Map<EventInfo_s, Node> simpleNodeMap = new HashMap<EventInfo_s, Node>();

	Map<ConditionalRelationship_s, Relationship> vConditionalRelationships = new HashMap<ConditionalRelationship_s, Relationship>();
	Map<SimpleRelationship_s, Relationship> vSimpleRelationships = new HashMap<SimpleRelationship_s, Relationship>();
	private String strNoInfo = "NA";

	public void readConditionalRelData() throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(
				EVENT_CONDITIONAL_PROBABILITIES))) {

			for (String line; (line = br.readLine()) != null;) {
				// process the line.
				String delims = "[\t]";
				String[] tokens = line.split(delims);

				for (int i = 0; i < tokens.length; i++) {

					if (tokens[i].equals("")) {
						tokens[i] = tokens[i].concat(strNoInfo);
					}
					tokens[i] = tokens[i].toUpperCase().trim();
				}

				double fSampleNo = Double.parseDouble(tokens[5]);
				double fProbVal = Double.parseDouble(tokens[10]);

				if ((fSampleNo > 0) && (fProbVal > 0)
						&& (!tokens[4].equals("NO"))
						&& (!tokens[9].equals("NO"))
						&& (!tokens[1].equals(strNoInfo))
						&& (!tokens[6].equals(strNoInfo))) {
					EventInfo_s strMolecEv1Pair = new EventInfo_s(tokens[1],
							tokens[2]);
					Node molecEv1 = null;

					if (!molecNodeMap.containsKey(strMolecEv1Pair)) {
						molecEv1 = createMolecularEventNode(strMolecEv1Pair);
					} else {
						molecEv1 = molecNodeMap.get(strMolecEv1Pair);
					}

					EventInfo_s strMolecEv2Pair = new EventInfo_s(tokens[6],
							tokens[7]);
					Node molecEv2 = null;

					if (!molecNodeMap.containsKey(strMolecEv2Pair)) {
						molecEv2 = createMolecularEventNode(strMolecEv2Pair);
					} else {
						molecEv2 = molecNodeMap.get(strMolecEv2Pair);
					}

					RelTypes szDiseaseStage = Utils
							.convertStringToRelType(tokens[11]);
					ConditionalRelationship_s dp = new ConditionalRelationship_s(
							strMolecEv1Pair, strMolecEv2Pair, szDiseaseStage);

					if (!vConditionalRelationships.containsKey(dp)) {
						vConditionalRelationships
								.put(dp, molecEv1.createRelationshipTo(
										molecEv2, szDiseaseStage));
						fProbVal = Math.round(fProbVal * 100.0) / 100.0;
						vConditionalRelationships.get(dp).setProperty(
								"CondProbValue", fProbVal);
						vConditionalRelationships.get(dp).setProperty("Pmid",
								tokens[0]);
						vConditionalRelationships.get(dp).setProperty(
								"TestedSampleNo", fSampleNo);
					}

					else {
						double fPrevProbVal = Double
								.parseDouble(vConditionalRelationships.get(dp)
										.getProperty("CondProbValue")
										.toString());
						double fPrevTestedSamplesNo = Double
								.parseDouble(vConditionalRelationships.get(dp)
										.getProperty("TestedSampleNo")
										.toString());
						String szPmidStr = vConditionalRelationships.get(dp)
								.getProperty("Pmid").toString();

						double fTotalSampleNo = fSampleNo
								+ fPrevTestedSamplesNo;
						double fNewProb = (fSampleNo * fProbVal + fPrevTestedSamplesNo
								* fPrevProbVal)
								/ fTotalSampleNo;
						fNewProb = Math.round(fNewProb * 100.0) / 100.0;
						vConditionalRelationships.get(dp).setProperty(
								"CondProbValue", fNewProb);
						vConditionalRelationships.get(dp).setProperty(
								"TestedSampleNo", fTotalSampleNo);
						vConditionalRelationships.get(dp).setProperty("Pmid",
								szPmidStr + "\t" + tokens[0]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readSimpleRelData() throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(
				EVENT_SIMPLE_PROBABILITIES))) {

			for (String line; (line = br.readLine()) != null;) {
				// process the line.
				String delims = "[\t]";
				String[] tokens = line.split(delims);

				for (int i = 0; i < tokens.length; i++) {

					if (tokens[i].equals("")) {
						tokens[i] = tokens[i].concat(strNoInfo);
					}
					tokens[i] = tokens[i].toUpperCase().trim();
				}

				double fProbVal = 0;
				if (!tokens[5].equals(strNoInfo)) {
					fProbVal = Double.parseDouble(tokens[5]);
				}

				double fSampleNo = 0;
				if (!tokens[6].equals(strNoInfo)) {
					fSampleNo = Double.parseDouble(tokens[6]);
				}

				if ((fProbVal > 0) && (fSampleNo > 0)
						&& (!tokens[4].equals("NO"))
						&& (!tokens[1].equals(strNoInfo))) {
					EventInfo_s strMolecEvPair = new EventInfo_s(tokens[1],
							tokens[2]);
					Node molecEv = null;

					if (!molecNodeMap.containsKey(strMolecEvPair)) {
						molecEv = createMolecularEventNode(strMolecEvPair);
					} else {
						molecEv = molecNodeMap.get(strMolecEvPair);
					}

					Node simpleMolecEv = null;

					if (!simpleNodeMap.containsKey(strMolecEvPair)) {
						simpleMolecEv = createStartEventNode(strMolecEvPair);
					} else {
						simpleMolecEv = simpleNodeMap.get(strMolecEvPair);
					}

					RelTypes szDiseaseStage = Utils
							.convertStringToRelType(tokens[7]);
					SimpleRelationship_s sp = new SimpleRelationship_s(
							strMolecEvPair, szDiseaseStage);

					if (!vSimpleRelationships.containsKey(sp)) {
						vSimpleRelationships.put(sp, simpleMolecEv
								.createRelationshipTo(molecEv, szDiseaseStage));
						fProbVal = Math.round(fProbVal * 100.0) / 100.0;
						vSimpleRelationships.get(sp).setProperty(
								"SimpleProbValue", fProbVal);

						vSimpleRelationships.get(sp).setProperty("Pmid",
								tokens[0]);
						vSimpleRelationships.get(sp).setProperty(
								"TestedSampleNo", fSampleNo);

					} else {
						double fPrevSimpleProbVal = Double
								.parseDouble(vSimpleRelationships.get(sp)
										.getProperty("SimpleProbValue")
										.toString());
						double fPrevTestedSamplesNo = Double
								.parseDouble(vSimpleRelationships.get(sp)
										.getProperty("TestedSampleNo")
										.toString());
						String szPmidStr = vSimpleRelationships.get(sp)
								.getProperty("Pmid").toString();

						double fTotalSampleNo = fSampleNo
								+ fPrevTestedSamplesNo;
						double fNewProb = (fSampleNo * fProbVal + fPrevTestedSamplesNo
								* fPrevSimpleProbVal)
								/ fTotalSampleNo;
						fNewProb = Math.round(fNewProb * 100.0) / 100.0;
						vSimpleRelationships.get(sp).setProperty(
								"SimpleProbValue", fNewProb);
						vSimpleRelationships.get(sp).setProperty(
								"TestedSampleNo", fTotalSampleNo);
						vSimpleRelationships.get(sp).setProperty("Pmid",
								szPmidStr + "\t" + tokens[0]);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generateStatistics() {

		displayCondRelationships();
		System.out.println();
		displaySimpleRestionaships();
	}

	private void displaySimpleRestionaships() {
		System.out.printf("Start events: %d\n", simpleNodeMap.size());

		int nSHeathlyRelNo = 0;
		int nSAdenomaRelNo = 0;
		int nSCarcinomaRelNo = 0;
		int nSPolypsRelNo = 0;
		int nSMetastasisRelNo = 0;

		for (Entry<SimpleRelationship_s, Relationship> eEvent : vSimpleRelationships
				.entrySet()) {

			String szRelType = eEvent.getValue().getType().name();
			if (szRelType.equals(RelTypes.HEALTHY.toString())) {
				nSHeathlyRelNo++;
			}
			if (szRelType.equals(RelTypes.ADENOMA.toString())) {
				nSAdenomaRelNo++;
			}
			if (szRelType.equals(RelTypes.CARCINOMA.toString())) {
				nSCarcinomaRelNo++;
			}
			if (szRelType.equals(RelTypes.POLYPS.toString())) {
				nSPolypsRelNo++;
			}
			if (szRelType.equals(RelTypes.METASTASIS.toString())) {
				nSMetastasisRelNo++;
			}
		}

		System.out.printf("HEALTHY: %d\n", nSHeathlyRelNo);
		System.out.printf("ADENOMA: %d\n", nSAdenomaRelNo);
		System.out.printf("CARCINOMA: %d\n", nSCarcinomaRelNo);
		System.out.printf("POLYPS: %d\n", nSPolypsRelNo);
		System.out.printf("METASTASIS: %d\n", nSMetastasisRelNo);
		System.out.printf("SIMPLE EVENTS: %d\n", vSimpleRelationships.size());
	}

	private void displayCondRelationships() {
		System.out.printf("Molecular events: %d\n", molecNodeMap.size());

		int nHeathlyRelNo = 0;
		int nAdenomaRelNo = 0;
		int nCarcinomaRelNo = 0;
		int nPolypsRelNo = 0;
		int nMetastasisRelNo = 0;

		for (Entry<ConditionalRelationship_s, Relationship> eEvent : vConditionalRelationships
				.entrySet()) {

			String szRelType = eEvent.getValue().getType().name();
			if (szRelType.equals(RelTypes.HEALTHY.toString())) {
				nHeathlyRelNo++;
			}
			if (szRelType.equals(RelTypes.ADENOMA.toString())) {
				nAdenomaRelNo++;
			}
			if (szRelType.equals(RelTypes.CARCINOMA.toString())) {
				nCarcinomaRelNo++;
			}
			if (szRelType.equals(RelTypes.POLYPS.toString())) {
				nPolypsRelNo++;
			}
			if (szRelType.equals(RelTypes.METASTASIS.toString())) {
				nMetastasisRelNo++;
			}
		}

		System.out.printf("HEALTHY: %d\n", nHeathlyRelNo);
		System.out.printf("ADENOMA: %d\n", nAdenomaRelNo);
		System.out.printf("CARCINOMA: %d\n", nCarcinomaRelNo);
		System.out.printf("POLYPS: %d\n", nPolypsRelNo);
		System.out.printf("METASTASIS: %d\n", nMetastasisRelNo);
		System.out.printf("CONDITIONAL EVENTS: %d\n",
				vConditionalRelationships.size());
	}

	private Node createMolecularEventNode(EventInfo_s _eventPair) {
		molecNodeMap.put(
				_eventPair,
				App.getGraphInstance().createNode(
						LabelTypes.MolecularEvent));
		molecNodeMap.get(_eventPair).setProperty("GeneSymbol",
				_eventPair.getGeneName());
		molecNodeMap.get(_eventPair).setProperty("EventName",
				_eventPair.getEventName());
		molecNodeMap.get(_eventPair).setProperty("MolecularEventId",
				_eventPair.getGeneName() + " " + _eventPair.getEventName());
		return molecNodeMap.get(_eventPair);
	}

	private Node createStartEventNode(EventInfo_s _eventPair) {
		simpleNodeMap.put(_eventPair, App.getGraphInstance()
				.createNode(LabelTypes.StartEvent));
		simpleNodeMap.get(_eventPair).setProperty("GeneSymbol",
				_eventPair.getGeneName());
		simpleNodeMap.get(_eventPair).setProperty("EventName",
				_eventPair.getEventName());
		simpleNodeMap.get(_eventPair).setProperty("StartEventId",
				_eventPair.getGeneName() + " " + _eventPair.getEventName());
		return simpleNodeMap.get(_eventPair);
	}

}
