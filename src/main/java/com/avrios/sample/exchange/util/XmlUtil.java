package com.avrios.sample.exchange.util;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * For iterating NodeList.
 * From https://stackoverflow.com/questions/14566596/loop-through-all-elements-in-xml-using-nodelist
 */
public final class XmlUtil {
    private XmlUtil() {
    }

    public static List<Node> asList(NodeList n) {
        return n.getLength() == 0 ?
                Collections.<Node>emptyList() : new NodeListWrapper(n);
    }

    static final class NodeListWrapper extends AbstractList<Node>
            implements RandomAccess {
        private final NodeList list;

        NodeListWrapper(NodeList l) {
            list = l;
        }

        public Node get(int index) {
            return list.item(index);
        }

        public int size() {
            return list.getLength();
        }
    }
}