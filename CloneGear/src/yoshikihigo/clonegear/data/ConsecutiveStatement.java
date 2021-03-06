package yoshikihigo.clonegear.data;

import java.util.ArrayList;
import java.util.List;

import yoshikihigo.clonegear.lexer.token.Token;

public class ConsecutiveStatement extends Statement {

	final public List<Statement> foldedStatements;

	public ConsecutiveStatement(final int fromLine, final int toLine,
			final int nestLevel, final boolean isTarget,
			final List<Token> tokens, final MD5 hash,
			final List<Statement> foldedStatements) {
		super(fromLine, toLine, nestLevel, isTarget, tokens, hash);
		this.foldedStatements = new ArrayList<>(foldedStatements);
	}
}
