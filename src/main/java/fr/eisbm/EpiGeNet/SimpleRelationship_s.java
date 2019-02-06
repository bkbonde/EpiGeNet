package fr.eisbm.EpiGeNet;

import fr.eisbm.EpiGeNet.App.RelTypes;

public class SimpleRelationship_s {
	
	protected final String _geneNameStr;
	protected final String _eventNameStr;
	protected final RelTypes _diseaseStageStr;

	public SimpleRelationship_s(EventInfo_s pair, RelTypes szDiseaseStage) {
		_geneNameStr = pair.getGeneName();
		_eventNameStr = pair.getEventName();
		_diseaseStageStr = szDiseaseStage;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((_diseaseStageStr == null) ? 0 : _diseaseStageStr.hashCode());
		result = prime * result
				+ ((_eventNameStr == null) ? 0 : _eventNameStr.hashCode());
		result = prime * result
				+ ((_geneNameStr == null) ? 0 : _geneNameStr.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SimpleRelationship_s)) {
			return false;
		}
		SimpleRelationship_s other = (SimpleRelationship_s) obj;
		if (_diseaseStageStr != other._diseaseStageStr) {
			return false;
		}
		if (_eventNameStr == null) {
			if (other._eventNameStr != null) {
				return false;
			}
		} else if (!_eventNameStr.equals(other._eventNameStr)) {
			return false;
		}
		if (_geneNameStr == null) {
			if (other._geneNameStr != null) {
				return false;
			}
		} else if (!_geneNameStr.equals(other._geneNameStr)) {
			return false;
		}
		return true;
	}


}
