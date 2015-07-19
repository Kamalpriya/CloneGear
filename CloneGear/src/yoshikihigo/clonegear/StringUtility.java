package yoshikihigo.clonegear;

import java.util.ArrayList;
import java.util.List;

import yoshikihigo.clonegear.data.Statement;
import yoshikihigo.clonegear.lexer.CLineLexer;
import yoshikihigo.clonegear.lexer.JavaLineLexer;
import yoshikihigo.clonegear.lexer.LineLexer;
import yoshikihigo.clonegear.lexer.PythonLineLexer;
import yoshikihigo.clonegear.lexer.token.Token;
import yoshikihigo.commentremover.CRConfig;
import yoshikihigo.commentremover.CommentRemover;
import yoshikihigo.commentremover.CommentRemoverJC;
import yoshikihigo.commentremover.CommentRemoverPY;

public class StringUtility {

	public static List<Statement> splitToStatements(final String text,
			final LANGUAGE language) {

		if (text.isEmpty()) {
			return new ArrayList<Statement>();
		}

		switch (language) {
		case JAVA: {
			final String[] args = new String[7];
			args[0] = "-q";
			args[1] = "-blankline";
			args[2] = "retain";
			args[3] = "-bracketline";
			args[4] = "retain";
			args[5] = "-indent";
			args[6] = "retain";
			final CRConfig config = CRConfig.initialize(args);
			final CommentRemover remover = new CommentRemoverJC(config);
			final String normalizedText = remover.perform(text);
			final LineLexer lexer = new JavaLineLexer();
			final List<Token> tokens = lexer.lexFile(normalizedText);
			final List<Statement> statements = Statement
					.getJCStatements(tokens);
			return statements;
		}
		case C:
		case CPP: {
			final String[] args = new String[7];
			args[0] = "-q";
			args[1] = "-blankline";
			args[2] = "retain";
			args[3] = "-bracketline";
			args[4] = "retain";
			args[5] = "-indent";
			args[6] = "retain";
			final CRConfig config = CRConfig.initialize(args);
			final CommentRemover remover = new CommentRemoverJC(config);
			final String normalizedText = remover.perform(text);
			final LineLexer lexer = new CLineLexer();
			final List<Token> tokens = lexer.lexFile(normalizedText);
			final List<Statement> statements = Statement
					.getJCStatements(tokens);
			return statements;
		}
		case PYTHON: {
			final String[] args = new String[3];
			args[0] = "-q";
			args[1] = "-blankline";
			args[2] = "retain";
			final CRConfig config = CRConfig.initialize(args);
			final CommentRemover remover = new CommentRemoverPY(config);
			final String normalizedText = remover.perform(text);
			final LineLexer lexer = new PythonLineLexer();
			final List<Token> tokens = lexer.lexFile(normalizedText);
			final List<Statement> statements = Statement
					.getPYStatements(tokens);
			return statements;
		}
		default: {
			System.err.println("invalid programming language.");
			System.exit(0);
		}
		}

		return null;
	}
}
