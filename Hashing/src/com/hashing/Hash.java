package com.hashing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Hash {

	private  Node[] arr=new Node[701];
	
	static class Node
	{
		Node p=null;
		Node next=null;
		Node prev=null;
		String key=null;
		int val=0;
	}
	
	public void insert(String key, int val)
	{
		Node node=new Node();
		node.key=key;
		node.val=val;
		
		int hashLoc=Math.abs(hashCode(key)%701);
		if(null==arr[hashLoc])
		{
			arr[hashLoc]=node;
		}
		else
		{
			Node nd=arr[hashLoc];
			boolean ow=false;
			do
			{
				if(nd.key.equals(key))
				{
					nd.val=val;
					ow=true;
					break;
				}
			}
			while((nd=nd.next)!=null);
			if(!ow)
			{
				nd=arr[hashLoc];
				node.next=nd;
				nd.prev=node;
				arr[hashLoc]=node;
			}
		}
	}
	
	public int find(String key)
	{
		int hashLoc=Math.abs(hashCode(key)%701);
		Node node=arr[hashLoc];
		
		if(null!=node){
		do
		{
			//System.out.println("node::"+node.key+"::"+node.next);
			if(node.key.equals(key))
				return node.val;
		}
		while((node=node.next)!=null);}
		return -1;
	}
	
	public void delete(String key)
	{
		int hashLoc=Math.abs(hashCode(key)%701);
		Node node=arr[hashLoc];
		if(null!=node){
		do
		{
			System.out.println("node::"+node.key+"::"+node.next);
			if(node.key.equals(key))
			{
				if(null!=node.prev)
					node.prev.next=node.next;
				else
					arr[hashLoc]=node.next;
				
				if(null!=node.next)					
					node.next.prev=node.prev;
			}
		}
		while((node=node.next)!=null);}
	}
	
	public void increase(String key)
	{
		int find=find(key);
		if(find==-1)
			insert(key,1);
		else
			insert(key,find+1);
	}
	
	public List listAllKeys()
	{
		List list=new ArrayList();
		for(Node nd:arr)
		{
			if(nd!=null)
			{
				do
				{
					list.add(nd.key);
					System.out.println("key: "+nd.key);
				}
				while((nd=nd.next)!=null);
			}
		}
		
		return list;
	}
	
	private int hashCode(String val)
	{
		char[] chars=val.toCharArray();
		int v=3;
		int hashVal=0;
		for(char c:chars)
		{
			hashVal=hashVal+(v*c);
			v=v*3;
		}
		return hashVal;
	}
	
	public static void main(String args[]) throws Exception
	{
		Hash hash=new Hash();
		
		hash.addToHash(hash, "src/aiwl.txt");
		hash.printValuesFromHash(hash, "src/out.txt");
		
	}
	
	private void addToHash(Hash hash, String file) throws Exception
	{
		FileReader fr=null;
		BufferedReader br=null;
		String sLine="";
		String[] strs=null;
		String rep=null;
		try
		{
			fr=new FileReader(new File(file));
			br=new BufferedReader(fr);
			while((sLine=br.readLine())!=null)
			{
				strs=sLine.split(" ");
				for(String str:strs)
				{
					rep=str.replaceAll("[^a-z^A-Z^0-9]+", "");
					hash.increase(rep);
				}
			}
		}
		finally
		{
			if(null!=fr)
				fr.close();
		}
		
	}
	
	private void printValuesFromHash(Hash hash, String fileName) throws Exception
	{
		StringBuffer buff=new StringBuffer();
		FileWriter fw=null;
		
		for(Node node:hash.arr)
		{
			if(null!=node)
			{
				
			do
			{
			//System.out.println(node.key+"::"+node.val);
			buff.append(node.key+"::"+node.val);
			buff.append(System.getProperty("line.separator"));
			}
			while((node=node.next)!=null);
			}
		}
		
		try
		{
			fw=new FileWriter(new File(fileName));
			fw.write(buff.toString());
		}
		finally
		{
			fw.close();
		}
	}
}
