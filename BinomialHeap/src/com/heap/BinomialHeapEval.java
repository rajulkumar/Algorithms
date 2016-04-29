package com.heap;

public class BinomialHeapEval {

	static class BinomialHeap{
		Node head=null;
	}
	
	
	static class Node{
		
		Node p=null;;
		int key=0;
		int degree=0;
		Node sibling=null;
		Node child=null;
	}
	
	public BinomialHeap makeHeap()
	{
		return new BinomialHeap();
	}
	
	public Node minHeap(BinomialHeap heap)
	{
		Node y=null;
		Node x=heap.head;
		int min=Integer.MAX_VALUE;
		while(null!=x)
		{
			if(x.key<min)
			{
				min=x.key;
				y=x;
			}
			x=x.sibling;
		}
		
		return y;
	}
	 
	private BinomialHeap binHeapMerge(BinomialHeap h1, BinomialHeap h2)
	{
		BinomialHeap h=makeHeap();
		Node x=null;
		Node y=null;
		Node temp=null;
		Node ptr=null;
		if(h1.head==null && h2.head==null)
		{
			return h;
		}
		else if(h1.head==null)
		{
			return h2;
		}
		else if(h2.head==null)
		{
			return h1;
		}
		
		x=h1.head;
		y=h2.head;
		if(x.degree<=y.degree)
			h.head=h1.head;
		else
			h.head=h2.head;
		
		while(x!=null && y!=null)
		{
			//System.out.println("vals::"+x.degree+"::"+y.degree);
			if(x.degree==y.degree )
			{
				if(null!=temp)
					temp.sibling=x;
				temp=x.sibling;
				x.sibling=y;
				x=temp;
				temp=y;
				y=y.sibling;
			}
			else if(x.degree<y.degree)
			{	
				if(null!=temp)
					temp.sibling=x;
				temp=x;
				x=x.sibling;
			}
			else
			{
				if(null!=temp)
					temp.sibling=y;
				temp=y;
				y=y.sibling;
			}
		}
		
		if(x==null && y!=null)
		{
			temp.sibling=y;
		}
		else if(y==null && x!=null)
		{
			temp.sibling=x;
		}
			
		return h;
	}
	
	private void binLink(Node x, Node y)
	{
		x.p=y;
		x.sibling=y.child;
		y.child=x;
		y.degree=y.degree+1;
	}
	
	public BinomialHeap union(BinomialHeap h1, BinomialHeap h2)
	{
		BinomialHeap h=makeHeap();
		h.head=binHeapMerge(h1, h2).head;
		
		if(h.head==null)
			return h;
		
		Node prev_x=null;
		Node x= h.head;
		Node next_x=x.sibling;
		
		while(null!=next_x)
		{
			if((x.degree!=next_x.degree) ||
				(next_x.sibling!=null && next_x.sibling.degree==x.degree))
			{
				prev_x=x;
				x=next_x;
			}
			else if(x.key<=next_x.key)
			{
				x.sibling=next_x.sibling;
				binLink(next_x, x);
			}
			else 
			{
				if(prev_x==null)
				{
					h.head=next_x;
				}
				else
				{
					prev_x.sibling=next_x;
				}
				binLink(x, next_x);
				x=next_x;
			}
			
			next_x=x.sibling;
		}
		
		return h;
	}
	
	public void insert(BinomialHeap heap,int x)
	{
		BinomialHeap h=makeHeap();
		Node node=new Node();
		
		node.key=x;
		h.head=node;
		heap.head=union(heap, h).head;
		//System.out.println("head::"+heap.head.key);
	}
	
	public Node extractMin(BinomialHeap heap)
	{
		Node y=null;
		Node prev=null;
		Node minP=null;
		Node x=heap.head;
		int min=Integer.MAX_VALUE;
		while(null!=x)
		{
			if(x.key<min)
			{
				min=x.key;
				y=x;
				minP=prev;
			}
			prev=x;
			x=x.sibling;
			
		}
		
		if(null!=minP)
			minP.sibling=y.sibling;
		else if(null!=y.sibling)
			heap.head=y.sibling;
		else
			heap.head=null;
		
		Node sib=y.child;
		if(null!=sib)
		{
			Node[] nodes=new Node[y.degree];
			for(int i=0;i<nodes.length;i++)
			{
				nodes[i]=sib;
				sib=sib.sibling;
			}
			
			BinomialHeap h=makeHeap();
			for(int i=nodes.length-1;i>0;i--)
			{
				nodes[i].p=null;
				nodes[i].sibling=nodes[i-1];
			}
			nodes[0].p=null;
			nodes[0].sibling=null;
			h.head=nodes[nodes.length-1];
			
			heap.head=union(heap, h).head;
			
		}
		
		return y;
	}
	
	public void decreaseKey(BinomialHeap heap, int x, int k)
	{
		int temp=0;
		if(k<x)
		{
			Node node=find(heap.head,x);
			node.key=k;
			Node y=node;
			Node p=y.p;
			while (null!=p && y.key<p.key){
				temp=y.key;
				y.key=p.key;
				p.key=temp;
				y=p;
				p=y.p;
			}
		}
	}
	
	private Node find(Node node, int x)
	{
		Node find=null;
		while(node!=null)
		{
			if(node.key==x)
			{	
				return node;
			}
			else
			{
				Node sib=node.sibling;
				find=find(sib,x);
				
				if(null==find)
				{
					return find(node.child,x);
				}
				else
				{
					return find;
				}
			}
		}
		return null;
	}
	
	public void delete(BinomialHeap heap, int x)
	{
		decreaseKey(heap, x, Integer.MIN_VALUE);
		extractMin(heap);
	}
	public static void main(String args[])
	{

		BinomialHeapEval eval=new BinomialHeapEval();
		BinomialHeap heap=new BinomialHeap();
		eval.insert(heap, 52);
		eval.insert(heap, 12);
		eval.insert(heap, 33);
		eval.insert(heap, 25);
		
		eval.print(heap.head);
		System.out.println("--------------------------------------------------------------");
		//BinomialHeapEval eval2=new BinomialHeapEval();
		BinomialHeap heap2=new BinomialHeap();
		eval.insert(heap2, 13);
		
		eval.print(heap2.head);
		
		System.out.println("--------------------------------------------------------------");
		eval.insert(heap2, 24);
		eval.print(heap2.head);
		
		System.out.println("--------------------------------------------------------------");
		eval.insert(heap2, 47);
		eval.print(heap2.head);
		
		System.out.println("--------------------------------------------------------------");
		//eval.insert(heap, 2);
		eval.insert(heap2, 9);
		eval.print(heap2.head);
		
		System.out.println("--------------------------------------------------------------");
		eval.insert(heap2, 88);
		eval.print(heap2.head);
		
		System.out.println("--------------------------------------------------------------");
		
		//eval.delete(heap, 2);
		eval.print(heap2.head);
		System.out.println("--------------------------------------------------------------");
		BinomialHeap fin=eval.union(heap, heap2);
		
		eval.print(fin.head);
		System.out.println("--------------------------------------------------------------");
		eval.extractMin(fin);
		eval.print(fin.head);
		System.out.println("--------------------------------------------------------------");
		
		eval.decreaseKey(fin, 47, 2);
		eval.print(fin.head);
		System.out.println("--------------------------------------------------------------");
		
		eval.delete(fin, 88);
		eval.print(fin.head);
		System.out.println("--------------------------------------------------------------");
	}
	
	private void print(Node node)
	{
		
			Node find=null;
			if(node!=null)
			{
					System.out.println("Node::"+node.key+"::"+node.degree+"::"+(node.p==null?null:node.p.key)+
						"::"+(node.sibling==null?null:node.sibling.key)+"::"+(node.child==null?null:node.child.key));
				
					Node sib=node.sibling;
					print(node.child);
					print(sib);
					
			}		
				
					
				
			
	
		
	}
}
