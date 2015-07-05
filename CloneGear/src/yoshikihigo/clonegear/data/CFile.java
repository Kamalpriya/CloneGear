package yoshikihigo.clonegear.data;

import yoshikihigo.clonegear.LANGUAGE;

public class CFile extends SourceFile {

	public CFile(final String path) {
		super(path);
	}

	@Override
	public LANGUAGE getLanguage() {
		return LANGUAGE.C;
	}
}
