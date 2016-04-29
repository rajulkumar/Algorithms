package com.flow;

import java.util.ArrayList;
import java.util.List;


public class PushRelabelMaxFlow{
	
	List<Vertex> ver=new ArrayList<PushRelabelMaxFlow.Vertex>();
	List<Edge> edge=new ArrayList<PushRelabelMaxFlow.Edge>();
	int V;
	
	class Edge{
		int flow;
		int capacity;
		int u;
		int v;
	}
	
	class Vertex{
		int h;
		int e;
	}
	
	public PushRelabelMaxFlow(int v)
	{
		this.V=v;
		for(int i=0;i<V;i++)
		{
			Vertex vert=new Vertex();
			vert.h=0;
			vert.e=0;
			ver.add(vert);
		}
	}
	
	private void addEdge(int u, int v, int capacity)
	{
		Edge e=new Edge();
		e.flow=0;
		e.capacity=capacity;
		e.u=u;
		e.v=v;
		edge.add(e);
	}
	
	private void preFlow(int s)
	{
		ver.get(s).h=ver.size();
		
		for(int i=0; i<edge.size();i++){
			if(edge.get(i).u==s)
			{
				edge.get(i).flow=edge.get(i).capacity;
				ver.get(edge.get(i).v).e +=edge.get(i).flow;
				
				Edge ed=new Edge();
				ed.flow=edge.get(i).flow*-1;
				ed.capacity=0;
				ed.v=s;
				ed.u=edge.get(i).v;
				edge.add(ed);
			}
		}
	}
	
	private int overflowVert()
	{
		for(int i=1;i<ver.size()-1;i++)
		{
			if(ver.get(i).e>0)
				return i;
		}
		return -1;
	}
	
	private void updateReverseEdgeFlow(int i, int flow)
	{
		int u=edge.get(i).v;
		int v=edge.get(i).u;
		for (int j = 0; j < edge.size(); j++)
	    {
	        if (edge.get(j).v == v && edge.get(j).u == u)
	        {
	            edge.get(j).flow -= flow;
	            return;
	        }
	    }
	 
	    // adding reverse Edge in residual graph
	    Edge e = new Edge();
	    e.flow=0;
	    e.capacity=flow;
	    e.u=u;
	    e.v=v;
	    edge.add(e);
	}
	
	private boolean push(int u)
	{
		for(int i=0; i<edge.size();i++){
			if(edge.get(i).u==u)
			{
				if(edge.get(i).flow==edge.get(i).capacity)
					continue;
				if(ver.get(u).h>ver.get(edge.get(i).v).h)
				{
					int flow = Math.min(edge.get(i).capacity - edge.get(i).flow,ver.get(u).e);

             // Reduce excess flow for overflowing vertex
					ver.get(u).e -= flow;

             // Increase excess flow for adjacent
					ver.get(edge.get(i).v).e += flow;

             // Add residual flow (With capacity 0 and negative
             // flow)
					edge.get(i).flow += flow;

					updateReverseEdgeFlow(i, flow);

					return true;
				}
			}
		}
		return false;
	}
	
	private void relabel(int u) {

		int mh = Integer.MAX_VALUE;

		// Find the adjacent with minimum height
		for (int i = 1; i < edge.size(); i++) {
			//System.out.println("edge out u::"+edge.get(i).u);
			if (edge.get(i).u == u) {

				// if flow is equal to capacity then no
				// relabeling
				if (edge.get(i).flow == edge.get(i).capacity)
					continue;

				// Update minimum height
				if (ver.get(edge.get(i).v).h < mh) 
				{
					mh = ver.get(edge.get(i).v).h;

				}
			}
		}
		
		ver.get(u).h = mh + 1;
	}
	
	private int maxFlow(int s, int t)
	{
		int u,i=0;
		preFlow(s);
		//System.out.println("pre flow done::");
		while(overflowVert()!=-1)
		{
			u=overflowVert();
			//System.out.println("u::"+u);
			if(!push(u))
				relabel(u);
			
			i++;
		}
		
		return ver.get(ver.size()-1).e;
		
	}
	
	public static void main(String args[])
	{
		int V = 6;
	    PushRelabelMaxFlow flow=new PushRelabelMaxFlow(V);
	 
	    // Creating above shown flow network
	    flow.addEdge(0, 1, 16);
	    flow.addEdge(0, 2, 13);
	    flow.addEdge(1, 2, 10);
	    flow.addEdge(2, 1, 4);
	    flow.addEdge(1, 3, 12);
	    flow.addEdge(2, 4, 14);
	    flow.addEdge(3, 2, 9);
	    flow.addEdge(3, 5, 20);
	    flow.addEdge(4, 3, 7);
	    flow.addEdge(4, 5, 4);
	    
	    System.out.println("max::"+flow.maxFlow(0, 5));
	    
	    for(Edge e:flow.edge)
	    {
	    	System.out.println("e::"+e.flow+"::"+e.capacity+"::"+e.u+"::"+e.v);
	    }
	    System.out.println("----------------------------------------------------");
	    
	    for(int i=0;i<flow.ver.size();i++)
	    {
	    	Vertex v=flow.ver.get(i);
	    	System.out.println("v:"+i+"::"+v.e+"::"+v.h);
	    }
	    
	}
}