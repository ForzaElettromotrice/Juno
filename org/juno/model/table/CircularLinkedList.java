package org.juno.model.table;

/**
 * Defines: CircularLinkedList class,
 * <p>
 * this class has been created only for this project
 * it can't be used as a true circular linked list since
 * it doesn't have some core methods (remove, set etc..)
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class CircularLinkedList<T>
{
	private static class Node<T>
	{
		private Node<T> prev;
		private T value;
		private Node<T> next;

		private Node(T val)
		{
			value = val;
		}
	}

	private Node<T> head;
	private Node<T> tail;
	private int size;

	private Node<T> currentNode;

	public void setFirst()
	{
		currentNode=head;
	}

	private void add(Node<T> newElement)
	{
		newElement.next = head;
		head.prev = newElement;
		newElement.prev = tail;
		tail.next = newElement;
	}

	public void addFirst(T value)
	{
		Node<T> newElement = new Node<>(value);
		if(isEmpty()) add(newElement);
		else tail = newElement;

		head = newElement;
		size++;
	}

	public void addLast(T value)
	{
		Node<T> newElement = new Node<>(value);
		if(isEmpty()) add(newElement);
		else head = newElement;

		tail = newElement;
		size++;
	}

	public void add(int n, T value)
	{
		if(n == 0)
		{
			addFirst(value);
			return;
		}
		if(n >= size)
		{
			addLast(value);
			return;
		}
		if(head == null)
		{
			addFirst(value);
			return;
		}

		Node<T> dest = head;

		for (int i = 0; i < n; i++)
		{
			dest = dest.next;
		}

		Node<T> newElement = new Node<>(value);

		newElement.next = dest;
		newElement.prev = dest.prev;

		dest.prev = newElement;
		newElement.prev.next = newElement;


		size++;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public int getSize()
	{
		return size;
	}

	public T getFirst()
	{
		return head.value;
	}

	public T getLast()
	{
		return tail.value;
	}

	public T get(int n)
	{
		if(n >= size) throw new IndexOutOfBoundsException();
		Node<T> dest = head;

		for (int i = 0; i < n; i++)
		{
			dest = dest.next;
		}

		return dest.value;
	}

	public void clear()
	{
		head.prev=null;
		tail.next=null;
		for (Node<T> node = head; node != null;)
		{
			Node<T> next = node.next;
			node.value=null;
			node.next=null;
			node.prev=null;
			node=next;
		}
		size=0;
		head=tail=null;
	}

	public T getNext()
	{
		currentNode=currentNode.next;
		return currentNode.value;
	}
}
