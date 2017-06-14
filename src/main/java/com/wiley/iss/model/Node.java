package com.wiley.iss.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Node<T> {
	
	private Node<T> parent = null;
	private T name = null;
	
	private List<Node<T>> children = null;

	public Node() {
	}

	public Node(T name) {
		this.name = name;
	}

	public Node(T name, Node<T> parent) {
		this.name = name;
		this.parent = parent;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public void addChild(Node<T> child) {
		child.setParent(this);
		this.children.add(child);
	}

	public Node<T> addChildIfNotExists(T name) {
		Node<T> temp = new Node<>(name);
		if(children==null){
			this.children = new ArrayList<>();
			this.addChild(temp);
		}else{
			int index = children.indexOf(temp);
			if (index < 0) {
				this.addChild(temp);
			} else {
				temp = children.get(index);
			}
		}

		return temp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node<T> other = (Node) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Node [name=" + name + "]";
	}

	public T getname() {
		return this.name;
	}

	public void setname(T name) {
		this.name = name;
	}

	@JsonIgnore
	public boolean isRoot() {
		return (this.parent == null);
	}
	
	@JsonIgnore
	public boolean isLeaf() {
		return this.children.size() == 0;
	}

	public void removeParent() {
		this.parent = null;
	}
}
