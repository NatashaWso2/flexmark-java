package com.vladsch.flexmark.ast;

public interface ReferenceNode<R extends NodeRepository<B>, B extends Node, N extends Node> extends Comparable<B> {
    N getReferencingNode(Node node);
}
