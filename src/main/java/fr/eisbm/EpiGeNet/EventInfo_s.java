package fr.eisbm.EpiGeNet;

public class EventInfo_s {
	protected final String _geneNameStr;
	protected final String _eventNameStr;

	public EventInfo_s(String _geneName, String _eventName) {
		_geneNameStr = _geneName;
		_eventNameStr = _eventName;
	}

	public String getGeneName() {
		return _geneNameStr;
	}

	public String getEventName() {
		return _eventNameStr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((_geneNameStr == null) ? 0 : _geneNameStr.hashCode());
		result = prime * result
				+ ((_eventNameStr == null) ? 0 : _eventNameStr.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		if (!(obj instanceof EventInfo_s)) {
			return false;
		}
		EventInfo_s other = (EventInfo_s) obj;
		if (_geneNameStr == null) {
			if (other._geneNameStr != null) {
				return false;
			}
		} else if (!_geneNameStr.equals(other._geneNameStr)) {
			return false;
		}
		if (_eventNameStr == null) {
			if (other._eventNameStr != null) {
				return false;
			}
		} else if (!_eventNameStr.equals(other._eventNameStr)) {
			return false;
		}
		return true;
	}

	public boolean isSimilar(Object obj) {
		if (!(obj instanceof EventInfo_s))
			return false;
		EventInfo_s p1 = (EventInfo_s) obj;

		if ((p1._geneNameStr.equals(this._geneNameStr) && p1._eventNameStr
				.equals(this._eventNameStr))
				|| (p1._eventNameStr.equals(this._geneNameStr) && p1._geneNameStr
						.equals(this._eventNameStr)))

		{
			return (true);
		}

		return (false);
	}
}
