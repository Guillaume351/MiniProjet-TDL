package fr.n7.stl.block;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java.lang.*;
import java.io.InputStreamReader;

%%

%class Lexer
%implements sym
%public
%unicode
%line
%column
%cup
%char
%{
    public Lexer(ComplexSymbolFactory _symbolFactory, java.io.InputStream _inputStream){
		this(_inputStream);
        this.symbolFactory = _symbolFactory;
    }

	public Lexer(ComplexSymbolFactory _symbolFactory, java.io.Reader _reader){
		this(_reader);
        this.symbolFactory = _symbolFactory;
    }
    
    private StringBuffer sb;
    private ComplexSymbolFactory symbolFactory;
    private int csline,cscolumn;

    public Symbol symbol(String name, int code){
		return symbolFactory.newSymbol(name, code,
						new Location(yyline+1,yycolumn+1, yychar), // -yylength()
						new Location(yyline+1,yycolumn+yylength(), yychar+yylength())
				);
    }
    public Symbol symbol(String name, int code, String lexem){
		return symbolFactory.newSymbol(name, code, 
						new Location(yyline+1, yycolumn +1, yychar), 
						new Location(yyline+1,yycolumn+yylength(), yychar+yylength()), lexem);
    }
    
    protected void emit_warning(String message){
    		System.out.println("scanner warning: " + message + " at : 2 "+ 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
    
    protected void emit_error(String message){
    		System.out.println("scanner error: " + message + " at : 2" + 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
%}

PassageLigne	= \r | \n | \r\n
Blanc 			= [ \t\f] | {PassageLigne}
Entier     		= 0|([1-9][0-9]*)
Flottant    	= {Entier}\.{Entier}

/* comments */
Commentaire 			= {BlocCommentaire} | {CommentaireFinLigne}
BlocCommentaire 		= "/*" {ContenuCommentaire} \*+ "/"
CommentaireFinLigne 	= "//" [^\r\n]* {PassageLigne}
ContenuCommentaire 	= ( [^*] | \*+[^*/] )*
Chaine				= \" ([^\"] | \\\") * \"
Caractere			= \' ([^\\] | \\\\) \'

Identificateur = ([:jletter:] | "_" ) ([:jletterdigit:] | [:jletter:] | "_" )*


%eofval{
    return symbolFactory.newSymbol("EOF",sym.EOF);
%eofval}

%state CODESEG

%%  

<YYINITIAL> {

  {Blanc} 			{ }
  {Commentaire} 		{ }
  ";"          		{ return symbolFactory.newSymbol("Point Virgule", UL_Point_Virgule); }
  ","          		{ return symbolFactory.newSymbol("Virgule", UL_Virgule); }
  "."          		{ return symbolFactory.newSymbol("Point", UL_Point); }
  ":"          		{ return symbolFactory.newSymbol("Deux Points", UL_Deux_Points); }
  "?"          		{ return symbolFactory.newSymbol("Point d'Interrogation", UL_Point_Interrogation); }
  "!"          		{ return symbolFactory.newSymbol("Point d'Exclamation", UL_Point_Exclamation); }
  "="				{ return symbolFactory.newSymbol("Egal", UL_Egal); }
  "=="				{ return symbolFactory.newSymbol("Double Egal", UL_Double_Egal); }
  "!="				{ return symbolFactory.newSymbol("Exclamation Egal", UL_Exclamation_Egal); }
  "<"				{ return symbolFactory.newSymbol("Inf??rieur", UL_Inferieur); }
  "<="				{ return symbolFactory.newSymbol("Inf??rieur Egal", UL_Inferieur_Egal); }
  ">"				{ return symbolFactory.newSymbol("Sup??rieur", UL_Superieur); }
  ">="				{ return symbolFactory.newSymbol("Sup??rieur Egal", UL_Superieur_Egal); }
  "+"          		{ return symbolFactory.newSymbol("Plus", UL_Plus); }
  "++"          		{ return symbolFactory.newSymbol("Double Plus", UL_Double_Plus); }
  "-"          		{ return symbolFactory.newSymbol("Moins", UL_Moins); }
  "--"          		{ return symbolFactory.newSymbol("Double Moins", UL_Double_Moins); }
  "*"          		{ return symbolFactory.newSymbol("Asterisque", UL_Asterisque); }
  "/"          		{ return symbolFactory.newSymbol("Oblique", UL_Oblique); }
  "%"          		{ return symbolFactory.newSymbol("Pour Cent", UL_Pour_Cent); }
  "||"         		{ return symbolFactory.newSymbol("Double Barre", UL_Double_Barre); }
  "&"          		{ return symbolFactory.newSymbol("Esperluette", UL_Esperluette); }
  "&&"         		{ return symbolFactory.newSymbol("Double Esperluette", UL_Double_Esperluette); }
  "("          		{ return symbolFactory.newSymbol("Parenthese Ouvrante", UL_Parenthese_Ouvrante); }
  ")"          		{ return symbolFactory.newSymbol("Parenthese Fermante", UL_Parenthese_Fermante); }
  "{"          		{ return symbolFactory.newSymbol("Accolade Ouvrante", UL_Accolade_Ouvrante); }
  "}"          		{ return symbolFactory.newSymbol("Accolade Fermante", UL_Accolade_Fermante); }
  "["          		{ return symbolFactory.newSymbol("Crochet Ouvrant", UL_Crochet_Ouvrant); }
  "]"          		{ return symbolFactory.newSymbol("Crochet Fermant", UL_Crochet_Fermant); }
  "const"			{ return symbolFactory.newSymbol("D??finition Constante", UL_Definition_Constante); }
  "typedef"			{ return symbolFactory.newSymbol("D??finition Type", UL_Definition_Type); }
  "struct"			{ return symbolFactory.newSymbol("Enregistrement", UL_Enregistrement); }
  "enum"				{ return symbolFactory.newSymbol("Enum??ration", UL_Enumeration); }
  "int"				{ return symbolFactory.newSymbol("Type Entier", UL_Type_Entier); }
  "float"			{ return symbolFactory.newSymbol("Type Entier", UL_Type_Flottant); }
  "character"		{ return symbolFactory.newSymbol("Type Caract??re", UL_Type_Caractere); }
  "String"			{ return symbolFactory.newSymbol("Type Cha??ne", UL_Type_Chaine); }
  "boolean"			{ return symbolFactory.newSymbol("Type Bool??en", UL_Type_Booleen); }
  "void"				{ return symbolFactory.newSymbol("Type Vide", UL_Type_Vide); }
  "if"				{ return symbolFactory.newSymbol("Si", UL_Si); }
  "else"				{ return symbolFactory.newSymbol("Sinon", UL_Sinon); }
  "print"			{ return symbolFactory.newSymbol("Afficher", UL_Afficher); }
  "new"				{ return symbolFactory.newSymbol("Nouveau", UL_Nouveau); }
  "null"				{ return symbolFactory.newSymbol("Nul", UL_Nul); }
  "true"				{ return symbolFactory.newSymbol("Vrai", UL_Vrai); }
  "false"			{ return symbolFactory.newSymbol("Faux", UL_Faux); }
  "return"			{ return symbolFactory.newSymbol("Retour", UL_Retour); }
  /* d??but retrait miniJava
  "fst"				{ return symbolFactory.newSymbol("Premier", UL_Premier); }
  "snd"				{ return symbolFactory.newSymbol("Second", UL_Second); }
  fin retrait miniJava */
  "while"			{ return symbolFactory.newSymbol("Tant que", UL_Tant_Que); }
  /* d??but ajout miniJava */
  "class"			{ return symbolFactory.newSymbol("Classe", UL_Classe); }
  "interface"		{ return symbolFactory.newSymbol("Interface", UL_Interface); }
  "extends"			{ return symbolFactory.newSymbol("H??rite de", UL_Herite); }
  "implements"		{ return symbolFactory.newSymbol("R??alise", UL_Realise); }
  "public"			{ return symbolFactory.newSymbol("Public", UL_Public); }
  "protected"		{ return symbolFactory.newSymbol("Prot??g??", UL_Protege); }
  "private"			{ return symbolFactory.newSymbol("Priv??", UL_Prive); }
  "static"			{ return symbolFactory.newSymbol("Public", UL_De_Classe); }
  "final"			{ return symbolFactory.newSymbol("D??finitif", UL_Definitif); }
  "this"			{ return symbolFactory.newSymbol("Moi", UL_Moi); }
  "super"			{ return symbolFactory.newSymbol("Super", UL_Super); }
  "Main"			{ return symbolFactory.newSymbol("Classe Principale", UL_Classe_Principale); }
  "main"			{ return symbolFactory.newSymbol("M??thode Principale", UL_Methode_Principale); }
  /* fin ajout miniJava */
  {Caractere}		{ return symbolFactory.newSymbol("Caract??re", UL_Caractere, yytext()); }
  {Chaine}			{ return symbolFactory.newSymbol("Chaine de caract??res", UL_Chaine, yytext()); }
  {Entier}     		{ return symbolFactory.newSymbol("Nombre Entier", UL_Nombre_Entier, yytext()); }
  {Flottant}     	{ return symbolFactory.newSymbol("Nombre Flottant", UL_Nombre_Flottant, yytext()); }
  {Identificateur}	{ return symbolFactory.newSymbol("Identificateur", UL_Identificateur, yytext()); }
}



// error fallback
.|\n          		{ emit_warning( "Unrecognized character '" + yytext() + "' -- ignored" ); }