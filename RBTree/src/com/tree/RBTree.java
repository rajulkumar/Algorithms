package com.tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

public class RBTree {

	private Node root=null;
	
	 class Node
	{
		Node p=null;
		Node left=null;
		Node right=null;
		int val=-1;
		String color=null;
	}
	
	public void insert(int val)
	{
		Node node=new Node();
		node.val=val;
		if(null==root)
		{
			node.color="B";
			root=node;
		}
		else
		{
			Node z=root;
			Node y=null;
			do
			{
				y=z;
				if(val<z.val)
					z=z.left;
				else
					z=z.right;
			}
			while(z!=null);
			node.p=y;
			node.color="R";
			if(y.val<node.val)
				y.right=node;
			else
				y.left=node;
			
			insertFix(node);
			printTree(root);
			System.out.println("-----------------------------------------------------------");
		}
	}
	
	private void insertFix(Node z)
	{
		
		Node y=null;
		if(null!=z.p && null!=z.p.p)
		{
			System.out.println("z::"+z.val+"::"+z.p.val+"::"+z.p.color);
			System.out.println("bool::"+z.p.color.equals("R")+"::"+(z.p==z.p.p.left));
		while("R".equals(z.p!=null?z.p.color:null))
		{
			if (z.p == (z.p.p!=null?z.p.p.left:null))
			{
				y =(z.p.p!=null?z.p.p.right:null);
				if ("R".equals(y!=null?y.color:null))
				{
						z.p.color = "B"; 
						y.color = "B";
						z.p.p.color = "R";
						z = z.p.p;
				}
				else if (z == z.p.right)
				{
						z = z.p;
						if(root==z)
						{
							root=z.right;
						}
						System.out.println("rotate left::"+root+"::"+z.right);
						leftRotate(z);
				}
				else
				{
						z.p.color = "B";
						z.p.p.color = "R";
						if(z.p.p== root)
						{
							root=z.p.p.left;
						}
						rightRotate(z.p.p);
				}
			}
			else
			{
				
				y=(z.p.p!=null?z.p.p.left:null);
				if ("R".equals(y!=null?y.color:null))
				{
						z.p.color="B";
						y.color="B";
						z.p.p.color="R";
						z=z.p.p;
				}
				else if (z==z.p.left)
				{
						z=z.p;
						if (root==z)
						{
							root=z.left;
						}
						rightRotate(z);
				}
				else
				{
						z.p.color="B";
						z.p.p.color="R";
						if(z.p.p==root)
						{
							root=z.p.p.right;
						}
						leftRotate(z.p.p);
				}
				
			}
		}
		root.color="B";	
		}
	}
	
	public void Sort()
	{
		inorderTrav(root);
	}
	private void inorderTrav(Node z)
	{
		
		if(null!=z)
		{
			//System.out.println("left::"+z.left.val);
			if(null!=z.left)
				inorderTrav(z.left);
			System.out.println("node::"+z.val+z.color);
			if(null!=z.right)
				inorderTrav(z.right);
		}
	}
	
	public Node search(int val)
	{
		Node node=searchTree(root, val);
		System.out.println("search::"+(node!=null?node.val:null));
		return node;
	}
	
	private Node searchTree(Node z,int val)
	{
		//System.out.println("z::"+z.val);
		if(z==null)
			return null;
		else if(z.val==val)
			return z;
		else if(z.val>val)
			return searchTree(z.left, val);
		else
			return searchTree(z.right,val);
				
	}
	
	public Node min()
	{
		Node min=minimum(root);
		System.out.println("min::"+min.val);
		return min;
	}
	
	private Node minimum(Node z)
	{
		
		if(z==null)
			return null;
		else if(z.left==null)
			return z;
		else 
			return minimum(z.left);
			
	}
	
	public Node max()
	{
		Node max=maximum(root);
		System.out.println("max::"+max.val);
		return max;
	}
	
	private Node maximum(Node z)
	{
		if(z==null)
			return null;
		else if (z.right==null)
			return z;
		else
			return maximum(z.right);
	}
	
	public Node successor(int val)
	{
		Node node=search(val);
		if(node.right!=null)
		{
			Node mini=minimum(node.right);
			System.out.println("successor::"+mini.val);
			return mini;
		}
		else
		{
			Node x = node;
			while(x.p!=null)
			{
				if(x==x.p.left)
				{
					System.out.println("successor::"+x.p.val);
					return x.p;
				}
				else
				{
					x=x.p;
				}
			}
		}
		return null;
	}
	
	public Node predecessor(int val)
	{
		
		Node node=search(val);
		if(node.left!=null)
		{
			Node max=maximum(node.left);
			System.out.println("predecessor::"+max.val);
			return max;
		}
		else
		{
			Node x=node;
			while(x.p!=null)
			{
				if(x==x.p.right)
				{
					System.out.println("predecessor::"+x.p.val);
					return x.p;
				}
				else
					x=x.p;
			}
		}
		
		return null;
	}
	
	private void leftRotate(Node x)
	{
		if(null!=x.right)
		{
		Node y = x.right; 
		x.right = y.left; 
		
		if (y.left!=null)
			y.left.p = x;
		
		y.p = x.p;
		
		if (x.p == null)
			root= y;
		else if (x == x.p.left)
			x.p.left = y;
		else 
			x.p.right = y;
		
		y.left = x;
		x.p = y;
		}
	}
	
	private void rightRotate(Node y)
	{
		if(null!=y.left)
		{
		Node x = y.left;
		y.left=x.right;
		
		if(x.right!=null)
			x.right.p=y;
		
		x.p=y.p;
		
		if(y.p == null)
			root=x;
		else if (y==y.p.left)
			y.p.left=x;
		else
			y.p.right=x;
		
		x.right=y;
		y.p=x;
		}
	}
	
	private int height(Node z)
	{
		
		return Math.max(1+(z.left!=null?height(z.left):0),
						1+(z.right!=null?height(z.right):0));
	}
	
	public static void main(String args[]) throws Exception
	{
		RBTree tree= new RBTree();
		
		FileReader fr=null;
		BufferedReader br=null;
		String line="";
		try
		{
			fr=new FileReader(new File("src/input.txt"));
			br=new BufferedReader(fr);
			while((line=br.readLine())!=null)
			{
				tree.insert(Integer.parseInt(line));
			}
			System.out.println("-----------------------------------------------------------");
			printTree(tree.root);
			System.out.println("-----------------------------------------------------------");
		}
		finally
		{
			br.close();
			fr.close();
		}
		
		boolean brk=true;
		while(brk)
		{
		System.out.println("Enter you option:");
		InputStreamReader is=new InputStreamReader(System.in);
		BufferedReader brd=new BufferedReader(is);
		String opt=brd.readLine();
		if("exit".equals(opt))
		{
			brk=false;
		}
		else
		{
		System.out.println("Enter arg (if any)");
		 is=new InputStreamReader(System.in);
		 brd=new BufferedReader(is);
		String arg=brd.readLine();
		
		int param=0;
		if(null!=arg && !"".equalsIgnoreCase(arg.trim()))
		{
			param=Integer.parseInt(arg);
		}
		Method[] method=RBTree.class.getDeclaredMethods();
		for(Method meth:method)
		{
			//System.out.println("meth::"+meth.getName());
			if(meth.getName().equals(opt))
			{
				if(param==0)
				{
					meth.invoke(tree,null);
				}
				else
				{
					meth.invoke(tree, param);
				}	
			}
		}
		}
		}
		System.out.println("End::");
		
	}
	
	private static void printTree(Node node)
	{
		System.out.println("Node::"+node.val+"::"+node.color+"::"+(null!=node.p?node.p.val:null)+"::"+
				(null!=node.left?node.left.val:null)+"::"+(null!=node.right?node.right.val:null));
		if(null!=node.left)
			printTree(node.left);
		if(null!=node.right)
			printTree(node.right);
	}
}

