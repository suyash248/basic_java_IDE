import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
class Note implements ActionListener,ListSelectionListener,CaretListener,KeyListener
{
	JFrame jf;
	JSplitPane split;
 	JTextArea tf,tarea;
	JScrollPane spt,spl;
 	JMenuBar menubar;
	JFileChooser jfc;
	static int flag=0,flagsave=0,fromindex=0,space,enter,start,unf;
	public File f;
	JLabel label,status,split_label;
	JList font_style,font,size;
	String path,original,original_redo,drive,cd,path_run,class_name,class_comp;
	JDialog d,run_f;
	JTextField tif,tir,fonttf,font_styletf,sizetf,run_class;
	JButton ok;	
	Image image;
	ImageIcon image_icon;
	Run r;
	Run1 r1;
	Run2 r2;
	Process p;
	Note()
	{}
 	Note(int font_size)
 	{
		try
  		{
   			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  		}catch(Exception e){}
		jf=new JFrame("Java IDE");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tf=new JTextArea();
		tarea=new JTextArea();
		tarea.setSelectionColor(Color.white);
		tarea.setSelectedTextColor(Color.blue);
		spl=new JScrollPane(tarea);
		tf.addCaretListener(this);
		tf.addKeyListener(this);
		spt=new JScrollPane(tf);
		split=new JSplitPane(JSplitPane.VERTICAL_SPLIT,spt,spl);
		split.setDividerLocation(520);
		menubar=new JMenuBar();
		String filemenuitemname[]={"New","Open","Save","Save As","Exit"};
		String editmenuitemname[]={"Undo","Redo","Cut","Copy","Paste","Delete","Find...","Replace...","Go To...","Select All"};
		String formatmenuitemname="Font";
		String toolsmenuitemname[]={"Run"};
		int keyevent[]={KeyEvent.VK_N,KeyEvent.VK_O,KeyEvent.VK_S,KeyEvent.VK_W,KeyEvent.VK_Q};
		int editkeyevent[]={KeyEvent.VK_Z,KeyEvent.VK_Y,KeyEvent.VK_X,KeyEvent.VK_C,KeyEvent.VK_V,KeyEvent.VK_DELETE,KeyEvent.VK_F,KeyEvent.VK_R,KeyEvent.VK_G,KeyEvent.VK_A};
		int toolskeyevent[]={KeyEvent.VK_F9};
		int accshortcut=ActionEvent.CTRL_MASK;
		//int accshortcut[]={ActionEvent.CTRL_MASK,ActionEvent.CTRL_MASK,ActionEvent.CTRL_MASK,ActionEvent.CTRL_MASK,ActionEvent.CTRL_MASK,ActionEvent.CTRL_MASK,ActionEvent.CTRL_MASK,ActionEvent.CTRL_MASK,ActionEvent.CTRL_MASK,ActionEvent.CTRL_MASK};
		int jshortcut[]={ActionEvent.CTRL_MASK,ActionEvent.ALT_MASK};
		JMenu edit=new JMenu("Edit");
		JMenu file=new JMenu("File");
		JMenu format=new JMenu("Format");
		JMenu tools=new JMenu("Tools");
		menubar.add(file);
		menubar.add(edit);
		menubar.add(format);
		menubar.add(tools);
		tf.setEditable(true);
		JMenuItem filemenuitem[]=new JMenuItem[filemenuitemname.length];
 		file.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem editmenuitem[]=new JMenuItem[editmenuitemname.length];
 		edit.setMnemonic(KeyEvent.VK_E);
		
		JMenuItem formatmenuitem=new JMenuItem(formatmenuitemname,KeyEvent.VK_F);
		formatmenuitem.addActionListener(this);
		format.add(formatmenuitem);
 		format.setMnemonic(KeyEvent.VK_O);
		
		JMenuItem toolsmenuitem[]=new JMenuItem[toolsmenuitemname.length];
		tools.setMnemonic(KeyEvent.VK_T);
		for(int i=0;i<filemenuitemname.length;i++)
		{
			filemenuitem[i]=new JMenuItem(filemenuitemname[i],keyevent[i]);
			filemenuitem[i].addActionListener(this);
			filemenuitem[i].setAccelerator(KeyStroke.getKeyStroke(keyevent[i],accshortcut));
			file.add(filemenuitem[i]);
		}
		for(int i=0;i<editmenuitemname.length;i++)
		{
			editmenuitem[i]=new JMenuItem(editmenuitemname[i],editkeyevent[i]);
			editmenuitem[i].addActionListener(this);
			editmenuitem[i].setAccelerator(KeyStroke.getKeyStroke(editkeyevent[i],accshortcut));
			edit.add(editmenuitem[i]);
		}
		for(int i=0;i<toolsmenuitemname.length;i++)
		{
			toolsmenuitem[i]=new JMenuItem(toolsmenuitemname[i],toolskeyevent[0]);
			toolsmenuitem[i].addActionListener(this);
			toolsmenuitem[i].setAccelerator(KeyStroke.getKeyStroke(toolskeyevent[0],jshortcut[i]));
			tools.add(toolsmenuitem[i]);
		}
		tf.setFont(new Font("Constantia",Font.PLAIN,font_size));
		jf.setJMenuBar(menubar);
		tf.setLineWrap(true);
		status=new JLabel("Ln : 1    Col : 1");
		status.setFont(new Font("Verdana",Font.PLAIN,14));
		tarea.setFont(new Font("OCR A",Font.PLAIN,14));
		tarea.setForeground(Color.red);
		jf.add(split,BorderLayout.CENTER);
		jf.add(status,BorderLayout.SOUTH);
		jf.setSize(1375,735);
		image_icon=new ImageIcon("D:\\Books\\JAVA\\JAVA Programs\\Java Editor\\icons\\note.png");
		image=image_icon.getImage();
		jf.setIconImage(image);
		jf.setVisible(true);
 	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("New"))
		{
			jf.setVisible(false);
			new Note(18);
		}
		if(e.getActionCommand().equals("Open"))
		{
			try
			{
				jfc=new JFileChooser();
				int x=jfc.showOpenDialog(null);
				if(flag==1)
				{
					tf.setText("");
				} 
				if(x==JFileChooser.APPROVE_OPTION)
				{
					tf.setText("");
					f=jfc.getSelectedFile();
					path=f.getPath();
					DataInputStream din=new DataInputStream(new FileInputStream(path));
			   		String s=" ";
					while(s!=null)
   					{
     						s=din.readLine();
    						if(s!=null)
						{
       				   			tf.append(s+"\n");
						}
   			  		}
					jf.setTitle(f.getName());
					Comp c=new Comp(tf,tarea);
					Thread t=new Thread(c,"compile");
					t.start();
				}
				flag=1;
				flagsave=1;
			}catch(Exception e1){}
		}
		if(e.getActionCommand().equals("Save"))
		{
			try
			{
				if(flagsave==0)
				{
					jfc=new JFileChooser();
					int x=jfc.showSaveDialog(null);
					if(x==JFileChooser.APPROVE_OPTION)
					{
						try
						{
							f=jfc.getSelectedFile();
							path=f.getPath();
							System.out.println(path);
							PrintStream ps=new PrintStream(new FileOutputStream(path));
							System.setErr(ps);
							for(int i=0;i<tf.getLineCount();i++)
							{
								String[] text=tf.getText().split("\\n");
								System.err.println(text[i]);
							}
							jf.setTitle(f.getName());
							flagsave=1;
							Comp c=new Comp(tf,tarea);
							Thread t=new Thread(c,"compile");
							t.start();
						}catch(Exception er){}
					}
				}
				else
				{
					PrintStream ps=new PrintStream(new FileOutputStream(this.path));
					System.setErr(ps);
					for(int i=0;i<tf.getLineCount();i++)
					{
						String[] text=tf.getText().split("\\n");
						System.err.println(text[i]);
					}
				}
			}catch(Exception e2){}
		}
		if(e.getActionCommand().equals("Save As"))
		{
			jfc=new JFileChooser();
			jfc.setDialogTitle("Save as");
			int x=jfc.showSaveDialog(null);
			if(x==JFileChooser.APPROVE_OPTION)
			{
				try
				{
					f=jfc.getSelectedFile();
					path=f.getPath();
					PrintStream ps=new PrintStream(new FileOutputStream(path));
					System.setErr(ps);
					for(int i=0;i<tf.getLineCount();i++)
					{
						String[] text=tf.getText().split("\\n");
						System.err.println(text[i]);
					}
					jf.setTitle(f.getName());
					flagsave=1;
					Comp c=new Comp(tf,tarea);
					Thread t=new Thread(c,"compile");
					t.start();
				}catch(Exception er){}
			}
		}
		if(e.getActionCommand().equals("Exit"))
		{
			System.exit(0);
		}
// For Edit menu...........
		if(e.getActionCommand().equals("Cut"))
		{
			original=tf.getText();
			tf.cut();
		}
		if(e.getActionCommand().equals("Copy"))
		{
			tf.copy();
		}
		if(e.getActionCommand().equals("Paste"))
		{
			original=tf.getText();
			tf.paste();
		}
		if(e.getActionCommand().equals("Delete"))
		{
			original=tf.getText();
			tf.replaceSelection("");
		}
		if(e.getActionCommand().equals("Find..."))
		{
			JDialog d=new JDialog(jf,"find");
			d.setSize(300,100);
			d.setLayout(new FlowLayout());
			d.setVisible(true);
			JLabel lab=new JLabel("Find what");
			tif=new JTextField(15);
			JButton b=new JButton("Find next");
			b.addActionListener(this);
			d.add(lab);
			d.add(tif);
			d.add(b);
			fromindex=0;
		}
		if(e.getActionCommand().equals("Find next"))
		{
			try{
				String search=tif.getText();
				int offset=tf.getText().indexOf(search,fromindex);
				String str=tf.getText().substring(offset,offset+search.length());
				if(search.equals(str))
					tf.select(offset,offset+search.length());  // One less. ie.select(0,4) will select from 0 to 3.
				fromindex=offset+search.length();
			}catch(Exception exc){}
		}
		if(e.getActionCommand().equals("Replace..."))
		{
			original=tf.getText();
			JDialog d=new JDialog(jf,"Replace");
			JPanel pf=new JPanel();
			JPanel pr=new JPanel();
			JPanel p3=new JPanel();
			tif=new JTextField(15);
			tir=new JTextField(15);
			JButton bf=new JButton("Find next");
			JButton br=new JButton("Replace");
			JButton bra=new JButton("Replace all");
			bf.addActionListener(this);
			br.addActionListener(this);
			bra.addActionListener(this);
			JLabel lf=new JLabel("Find what");
			JLabel lr=new JLabel("Replace with");
			pf.add(lf);
			pf.add(tif);
			pf.add(bf);
			pr.add(lr);
			pr.add(tir);
			pr.add(br);
			p3.add(bra);
			d.add(pf,BorderLayout.NORTH);
			d.add(pr,BorderLayout.CENTER);
			d.add(p3,BorderLayout.SOUTH);
			d.setVisible(true);
			d.pack();
		}
		if(e.getActionCommand().equals("Replace"))
		{
			try{
			original=tf.getText();
			String sel=tf.getSelectedText();
			tf.setText(tf.getText().replaceFirst(sel,tir.getText()));
			}catch(Exception exce){}
		}
		if(e.getActionCommand().equals("Replace all"))
		{
			original=tf.getText();
			tf.setText(tf.getText().replaceAll(tif.getText(),tir.getText()));
		}
		if(e.getActionCommand().equals("Undo"))
		{
			original_redo=tf.getText();
			tf.setText(original);
		}
		if(e.getActionCommand().equals("Redo"))
		{
			if(original_redo!=null)
				tf.setText(original_redo);
		}
		if(e.getActionCommand().equals("Select All"))
		{
			tf.selectAll();
		}
		if(e.getActionCommand().equals("OK"))
		{
			try{
			int fontstyle=0;
			String fsize=size.getSelectedValue().toString();
			if(font_style.getSelectedIndex()==0)
		    		    fontstyle=0;
			if(font_style.getSelectedIndex()==1)
				    fontstyle=1;
			if(font_style.getSelectedIndex()==2)
				    fontstyle=2;
			tf.setFont(new Font(font.getSelectedValue().toString(),fontstyle,Integer.valueOf(fsize)));
			d.setVisible(false);
			}catch(Exception ee){}
		}
		if(e.getActionCommand().equals("Font"))
		{
			try{
			GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
			Font[] allfonts=ge.getAllFonts();
			d=new JDialog(jf,"Font");
			JPanel pt=new JPanel();
			JPanel p1=new JPanel();
			JPanel p2=new JPanel();
			JButton bf=new JButton("OK");
			bf.addActionListener(this);
			String[] fontentries=new String[allfonts.length];
			String[] sizeentries={"10","14","16","18","20","24","26","32","36","48","54","68","72"};
			String[] fontstyleentries={"Regular","<html><B>Bold</B></html>","<html><I>Italic</I></html>",};
			for(int i=0;i<allfonts.length;i++)
			{
				fontentries[i]=allfonts[i].getFontName();
			}
			font=new JList(fontentries);
			font_style=new JList(fontstyleentries);
			size=new JList(sizeentries);
			font.setVisibleRowCount(6);
			font_style.setVisibleRowCount(6);
			size.setVisibleRowCount(6);
			font.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			font_style.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			size.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane fontscroll=new JScrollPane(font);
			JScrollPane sizescroll=new JScrollPane(size);
			JScrollPane fontstylescroll=new JScrollPane(font_style);
			fonttf=new JTextField(5);
			font_styletf=new JTextField(5);
			sizetf=new JTextField(5);
			JLabel ftf=new JLabel("font");
			JLabel fstyletf=new JLabel("font style");
			JLabel fsizetf=new JLabel("size");
			label=new JLabel("AaBbCcDd");
			font.addListSelectionListener(this);
			font_style.addListSelectionListener(this);
			size.addListSelectionListener(this);
			pt.add(ftf);
			pt.add(fonttf);
			pt.add(fstyletf);
			pt.add(font_styletf);
			pt.add(fsizetf);
			pt.add(sizetf);
			p1.add(fontscroll,FlowLayout.LEFT);
			p1.add(fontstylescroll,FlowLayout.CENTER);
			p1.add(sizescroll,FlowLayout.RIGHT);
			label.setBorder(BorderFactory.createTitledBorder("Sample"));
			p2.add(label,FlowLayout.LEFT);
			p2.add(bf);
			label.setFont(new Font("Georgia",Font.ITALIC,22));
			d.add(pt,BorderLayout.NORTH);
			d.add(p1,BorderLayout.CENTER);
			d.add(p2,BorderLayout.SOUTH);
			d.setVisible(true);
			d.setSize(400,400);
			}catch(Exception ec){}
		}
		if(e.getActionCommand().equals("Run"))
		{
			try{
				Comp c=new Comp(tf,tarea);
				Thread t1=new Thread(c,"compile");
				t1.start();
				JLabel lab=new JLabel("Enter the name of class containing main() ");
				ok=new JButton("OK");
				ok.addActionListener(this);
				run_f=new JDialog();
				run_f.setLayout(new BorderLayout());
				run_class=new JTextField(15);
				run_f.add(lab,BorderLayout.NORTH);
				run_f.add(run_class,BorderLayout.CENTER);
				run_f.add(ok,BorderLayout.SOUTH);
				run_f.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				run_f.pack();
				run_f.setVisible(true);
			}catch(Exception e2){System.out.println(e2+" from run");}
		}
		if(e.getSource()==ok)
		{
			try{
			path_run=run_class.getText();
			run_f.setVisible(false);
			p=Runtime.getRuntime().exec("cmd /c "+"cd .&&javaw "+path_run);
			r=new Run(tarea,p);
			r1=new Run1(tarea,p);
			r2=new Run2(tarea,p);
			Thread tr=new Thread(r,"run");
			Thread tr1=new Thread(r1,"run1");
			Thread tr2=new Thread(r2,"run2");
			tr.start();
			tr1.start();
			tr2.start();
			}catch(Exception e22){System.out.println(e22);}
		}
	}
	public void valueChanged(ListSelectionEvent event)
	{
		try{
		int fontstyle=0;
		String fontname=font.getSelectedValue().toString();
		String fstyle=font_style.getSelectedValue().toString();
		String fsize=size.getSelectedValue().toString();
		fonttf.setText(fontname);
		sizetf.setText(fsize);
		font_style.setFont(new Font(fontname,0,12));
		if(font_style.getSelectedIndex()==0)
		{
		    fontstyle=0;
		    font_styletf.setText("Regular");
		}
		if(font_style.getSelectedIndex()==1)
		{
		    fontstyle=1;
		    font_styletf.setText("Bold");
		}
		if(font_style.getSelectedIndex()==2)
		{
		    fontstyle=2;
		    font_styletf.setText("Italic");
		}
		label.setFont(new Font(fontname,fontstyle,Integer.valueOf(fsize)));
		}catch(Exception fgs){}
	}
	public void caretUpdate(CaretEvent e) 
	{
		int caretpos=1;
		int linenum=1;
		int columnnum=1;
		try{
		caretpos = tf.getCaretPosition();
           		linenum = tf.getLineOfOffset(caretpos);
		columnnum = caretpos - tf.getLineStartOffset(linenum);
		}catch(Exception es){System.out.println(es);}
		tarea.setText("");
		Comp c=new Comp(tf,tarea);
		Thread t=new Thread(c,"compile");
		t.start();
		status.setText("Ln : " + linenum + "    Col : " + columnnum);
	}
	public void keyReleased(KeyEvent e)
	{	
	}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE||e.getKeyCode()==KeyEvent.VK_DELETE)
		{
			if(unf==0)
			{
				original=tf.getText();
				unf=1;
			}
		}
		else
			unf=0;
	}	
	public void keyTyped(KeyEvent e)
	{
	}
	public static void main(String... w)
	{
		Runtime rt=Runtime.getRuntime();
		Shutdown sd=new Shutdown();
		rt.addShutdownHook(new Thread(sd));
		new Note(18);
	}
}
class Comp extends OutputStream implements Runnable
{
	String class_name;
	JTextArea tf,tarea;
	Process p;
	File f;
	String[] text;
	Comp(JTextArea tf,JTextArea tarea)
	{
		this.tf=tf;
		this.tarea=tarea;
	}
	public void run()
	{
		try{
			PrintStream out = new PrintStream(this);
			System.setOut( out );
			String tf_text=tf.getText();
			String search="public class ";
			int offset_p=tf_text.indexOf(search);
			int space=tf_text.indexOf(" ",offset_p+search.length());
			int enter=tf_text.indexOf("\n",offset_p+search.length());
			if(offset_p!=-1&&(enter!=-1||space!=-1))
			{
				if(enter!=-1&&Note.enter!=-1)
				{
					class_name=tf_text.substring(offset_p+search.length(),enter);
					FileOutputStream fout=new FileOutputStream("."+"\\"+class_name+".java");
					PrintStream ps=new PrintStream(fout);
					System.setErr(ps);
					for(int i=0;i<tf.getLineCount();i++)
					{
						text=tf.getText().split("\\n");
					}	
					for(int j=0;j<text.length;j++)	
						System.err.println(text[j]);
					p=Runtime.getRuntime().exec("cmd /c "+"cd .&&"+"javac "+class_name+".java");
					Note.space=-1;
				}
				else
				{
					if(space!=-1&&Note.space!=-1)
					{
						class_name=tf_text.substring(offset_p+search.length(),space);	
						FileOutputStream fout=new FileOutputStream("."+"\\"+class_name+".java");
						PrintStream ps=new PrintStream(fout);
						System.setErr(ps);
						for(int i=0;i<tf.getLineCount();i++)
						{
							text=tf.getText().split("\\n");
						}	
						for(int j=0;j<text.length;j++)	
							System.err.println(text[j]);
						p=Runtime.getRuntime().exec("cmd /c "+"cd .&&"+"javac "+class_name+".java");
						Note.enter=-1;
					}
				}
			}
			else
			{
				
				class_name="Test";
				FileOutputStream fout=new FileOutputStream("."+"\\"+class_name+".java");
				PrintStream ps=new PrintStream(fout);
				System.setErr(ps);
				for(int i=0;i<tf.getLineCount();i++)
				{
					text=tf.getText().split("\\n");
				}	
				for(int j=0;j<text.length;j++)	
					System.err.println(text[j]);
				p=Runtime.getRuntime().exec("cmd /c "+"cd .&&"+"javac "+class_name+".java");
			}
			p.waitFor();
			BufferedReader reader_error=new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line=reader_error.readLine(); 
			while(line!=null)
			{
				System.out.println(line+"\n");
				line=reader_error.readLine();
			}
			}catch(Exception e1)
			{	System.out.println(e1);
				System.out.println("getLineCount="+tf.getLineCount()+" from exception");
			}
	}
	public void write(int b) throws IOException
	{
		tarea.append( String.valueOf( ( char )b ) );
	}
}
class Run extends OutputStream implements Runnable
{
	String input="";
	JTextArea tf,tarea;
	Process p;
	Run(){}
	Run(JTextArea tarea,Process p)
	{
		this.tarea=tarea;
		this.p=p;
	}
	public void run()
	{
		try{
			PrintStream out= new PrintStream(this);
			System.setOut( out );
			tarea.setText("");
			InputStream in=p.getInputStream();
			BufferedReader reader=new BufferedReader(new InputStreamReader(in));
			String line1=reader.readLine(); 
			while(line1!=null)
			{
				System.out.print(line1+"\n");
				line1=reader.readLine();
			}		
			//in.close();
		}catch(Exception e2){System.out.println(e2+" run");}
	}
	public void write(int b) throws IOException
	{
		tarea.append( String.valueOf( ( char )b ) );
	}
}
class Run1 extends OutputStream implements Runnable
{
	String input="";
	JTextArea tarea;
	Process p;
	Run1(){}
	Run1(JTextArea tarea,Process p)
	{
		this.tarea=tarea;
		this.p=p;
	}
	public void run()
	{
		try{
			PrintStream out= new PrintStream(this);
			System.setOut( out );
			tarea.setText("");
			InputStream in=p.getErrorStream();
			BufferedReader reader_error=new BufferedReader(new InputStreamReader(in));
			String line=reader_error.readLine();
			while(line!=null)
			{
				System.out.print(line+"\n");
				line=reader_error.readLine();
			}
			//in.close();
		}catch(Exception e2){System.out.println(e2+" run1");}
	}
	public void write(int b) throws IOException
	{
		tarea.append( String.valueOf( ( char )b ) );
	}
}
class Run2 implements Runnable,KeyListener
{
	String input="";
	JTextArea tarea;
	Process p;
	PrintStream ps;
	OutputStream oo;
	Run2(){}
	Run2(JTextArea tarea,Process p)
	{
		this.tarea=tarea;
		this.p=p;
	}
	public void run()
	{
		try{
			tarea.setText("");
			tarea.addKeyListener(this);
			ps=new PrintStream(p.getOutputStream());
		}catch(Exception e2){System.out.println(e2+" run2");}
	}
	public void keyReleased(KeyEvent e)
	{
		try{
		char chr=e.getKeyChar();
		oo=p.getOutputStream();
		ps=new PrintStream(oo);
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{	
			ps.println(input);
			input="";
			oo.flush();
		}
		else if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE )
		{
			int len=input.length();
			char ch[]=new char[len-1];
			for(int i=0;i<ch.length;i++)
				ch[i]=input.charAt(i);
			input=new String(ch);
		}
		else if(e.getKeyCode()>=32&&e.getKeyCode()<=126)
		{
			if(e.getKeyCode()>=37&&e.getKeyCode()<=40)
				input=input;
			else
				input=input+String.valueOf( ( char )chr );
		}
		else
		{
			input=input;
		}
		}catch(Exception e12){System.out.println("Input is not allowed !!");}
	}
	public void keyTyped(KeyEvent e)
	{
	}
	public void keyPressed(KeyEvent e)
	{
	}
}
class Shutdown implements Runnable
{
	public void run()
	{
		try{
		Runtime.getRuntime().exec("cmd /c "+"del *.class");
		Runtime.getRuntime().exec("cmd /c "+"exit");
		Runtime.getRuntime().exec("cmd /c "+"del Test.java");
		}catch(Exception ee){System.out.println(ee);}
	}
}