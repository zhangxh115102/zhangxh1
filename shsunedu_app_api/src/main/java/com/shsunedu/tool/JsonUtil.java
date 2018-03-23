package com.shsunedu.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtil {
	private final ObjectMapper OBJECTMAPPER = new ObjectMapper();
	private final ObjectNode ROOT = OBJECTMAPPER.createObjectNode();
	private ObjectNode node = null;

	public ObjectNode initObjectNode() throws Exception {
		return OBJECTMAPPER.createObjectNode();
	}

	public ArrayNode initArrayNode() throws Exception {
		return OBJECTMAPPER.createArrayNode();
	}

	public ObjectNode createNode(String key, Object object) throws Exception {
		node = OBJECTMAPPER.createObjectNode();
		node.putPOJO(key, object);
		return node;
	}

	public void addToRoot(String key, Object value) throws Exception {
		ROOT.putPOJO(key, value);
	}

	public String toSuccessString() throws Exception {
		ROOT.put("success", 1000);
		ROOT.put("msg", "");
		return OBJECTMAPPER.writeValueAsString(ROOT);
	}

	public String toErrorString(int code, String msg) {
		try {
			ROOT.put("success", code);
			ROOT.put("msg", msg);
			return OBJECTMAPPER.writeValueAsString(ROOT);
		} catch (Exception e) {
			return recovery(code, msg);
		}
	}

	public String toErrorString(String msg) {
		try {
			ROOT.put("success", 3000);
			ROOT.put("msg", msg);
			return OBJECTMAPPER.writeValueAsString(ROOT);
		} catch (Exception e) {
			return recovery(3000, msg);
		}
	}
	
	public String toUserErrorString(String msg) {
		try {
			ROOT.put("success", 2000);
			ROOT.put("msg", msg);
			return OBJECTMAPPER.writeValueAsString(ROOT);
		} catch (Exception e) {
			return recovery(2000, msg);
		}
	}

	private String recovery(int code, String msg) {
		StringBuffer str = new StringBuffer().append("{\"status\":").append(code);

		if (msg != null)
			str.append(",\"msg\":\"").append(msg).append("\"}");
		else
			str.append("}");

		return str.toString();
	}

	public static void main(String[] args) {

		System.out.println(new JsonUtil().recovery(1, null));
	}
}
