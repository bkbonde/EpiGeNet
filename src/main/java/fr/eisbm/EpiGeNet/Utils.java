package fr.eisbm.EpiGeNet;

import fr.eisbm.EpiGeNet.App.RelTypes;

public class Utils {
	static public RelTypes convertStringToRelType(String string) {

		string = string.toUpperCase();
		if (string.contains("HEALTHY")) {
			return RelTypes.HEALTHY;
		}

		if (string.contains("ADENOMA") || string.contains("PREMALIGNANT")
				|| string.contains("INFLAMED")
				|| string.contains("CARCINOMA_IN_SITU")
				|| string.contains("ABERRANT CRYPT FOCUS")
				|| string.contains("ADJACENT TO ACF")) {
			return RelTypes.ADENOMA;
		}

		if (string.contains("POLYPS")) {
			return RelTypes.POLYPS;
		}

		if (string.contains("CARCINOMA") || string.contains("CRC") || string.contains("MACROPHAGES FROM TUMOR")) {
			return RelTypes.CARCINOMA;
		}
		if (string.contains("METASTASIS")) {
			return RelTypes.METASTASIS;
		}

		return RelTypes.eNoEvent;
	}

}
