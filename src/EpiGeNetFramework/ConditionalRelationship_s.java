package EpiGeNetFramework;

import EpiGeNetFramework.MainCCGraph.RelTypes;

public class ConditionalRelationship_s {

	protected final String _firstGeneNameStr;
	protected final String _firstEventNameStr;
	protected final String _secondGeneNameStr;
	protected final String _secondEventNameStr;
	protected final RelTypes _diseaseStageStr;

	public ConditionalRelationship_s(EventInfo_s pair1, EventInfo_s pair2, RelTypes szDiseaseStage) {
		_firstGeneNameStr = pair1.getGeneName();
		_firstEventNameStr = pair1.getEventName();
		_secondGeneNameStr = pair2.getGeneName();
		_secondEventNameStr = pair2.getEventName();
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
		result = prime
				* result
				+ ((_firstEventNameStr == null) ? 0 : _firstEventNameStr
						.hashCode());
		result = prime
				* result
				+ ((_firstGeneNameStr == null) ? 0 : _firstGeneNameStr
						.hashCode());
		result = prime
				* result
				+ ((_secondEventNameStr == null) ? 0 : _secondEventNameStr
						.hashCode());
		result = prime
				* result
				+ ((_secondGeneNameStr == null) ? 0 : _secondGeneNameStr
						.hashCode());
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
		if (!(obj instanceof ConditionalRelationship_s)) {
			return false;
		}
		ConditionalRelationship_s other = (ConditionalRelationship_s) obj;
		if (_diseaseStageStr != other._diseaseStageStr) {
			return false;
		}
		if (_firstEventNameStr == null) {
			if (other._firstEventNameStr != null) {
				return false;
			}
		} else if (!_firstEventNameStr.equals(other._firstEventNameStr)) {
			return false;
		}
		if (_firstGeneNameStr == null) {
			if (other._firstGeneNameStr != null) {
				return false;
			}
		} else if (!_firstGeneNameStr.equals(other._firstGeneNameStr)) {
			return false;
		}
		if (_secondEventNameStr == null) {
			if (other._secondEventNameStr != null) {
				return false;
			}
		} else if (!_secondEventNameStr.equals(other._secondEventNameStr)) {
			return false;
		}
		if (_secondGeneNameStr == null) {
			if (other._secondGeneNameStr != null) {
				return false;
			}
		} else if (!_secondGeneNameStr.equals(other._secondGeneNameStr)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConditionalRelationship_s [_firstGeneNameStr="
				+ _firstGeneNameStr + ", _firstEventNameStr="
				+ _firstEventNameStr + ", _secondGeneNameStr="
				+ _secondGeneNameStr + ", _secondEventNameStr="
				+ _secondEventNameStr + ", _diseaseStageStr="
				+ _diseaseStageStr + "]";
	}

	
	
}
