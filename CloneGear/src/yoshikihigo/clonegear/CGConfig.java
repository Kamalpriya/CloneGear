package yoshikihigo.clonegear;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class CGConfig {

	static private CGConfig SINGLETON = null;

	static public boolean initialize(final String[] args) {

		if (null != SINGLETON) {
			return false;
		}

		final Options options = new Options();

		{
			final Option source = new Option("src", "source", true,
					"source code of clone detection target");
			source.setArgName("sourcecode");
			source.setArgs(1);
			source.setRequired(false);
			options.addOption(source);
		}

		{
			final Option similarityOutput = new Option("sml", "similarity",
					true, "output file for similarities between clone sets");
			similarityOutput.setArgName("file");
			similarityOutput.setArgs(1);
			similarityOutput.setRequired(false);
			options.addOption(similarityOutput);
		}

		{
			final Option language = new Option("lang", "language", true,
					"programming language for analysis");
			language.setArgName("language");
			language.setArgs(1);
			language.setRequired(false);
			options.addOption(language);
		}

		{
			final Option threshold = new Option("thrld", "threshold", true,
					"threshold of detected clone size");
			threshold.setArgName("threshold");
			threshold.setArgs(1);
			threshold.setRequired(false);
			options.addOption(threshold);
		}

		{
			final Option software = new Option("soft", "software", true,
					"software name");
			software.setArgName("software");
			software.setArgs(1);
			software.setRequired(false);
			options.addOption(software);
		}

		{
			final Option thread = new Option("thd", "thread", true,
					"end revision of repository for test");
			thread.setArgName("thread");
			thread.setArgs(1);
			thread.setRequired(false);
			options.addOption(thread);
		}

		{
			final Option verbose = new Option("v", "verbose", false,
					"verbose output for progressing");
			verbose.setRequired(false);
			options.addOption(verbose);
		}

		{
			final Option result = new Option("result", "result", true,
					"clone detection results of CGFinder");
			result.setArgName("file");
			result.setArgs(1);
			result.setRequired(false);
			options.addOption(result);
		}

		{
			final Option debug = new Option("debug", "debug", false,
					"print some informlation for debugging");
			debug.setRequired(false);
			options.addOption(debug);
		}

		try {
			final CommandLineParser parser = new PosixParser();
			final CommandLine commandLine = parser.parse(options, args);
			SINGLETON = new CGConfig(commandLine);
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(0);
		}

		return true;
	}

	static public CGConfig getInstance() {

		if (null == SINGLETON) {
			System.err.println("Config is not initialized.");
			System.exit(0);
		}

		return SINGLETON;
	}

	private final CommandLine commandLine;

	private CGConfig(final CommandLine commandLine) {
		this.commandLine = commandLine;
	}

	public Set<LANGUAGE> getLANGUAGE() {

		final Set<LANGUAGE> languages = new HashSet<>();

		if (this.commandLine.hasOption("lang")) {
			final String option = this.commandLine.getOptionValue("lang");
			final StringTokenizer tokenizer = new StringTokenizer(option, ":");
			while (tokenizer.hasMoreTokens()) {
				try {
					final String value = tokenizer.nextToken();
					final LANGUAGE language = LANGUAGE.valueOf(value
							.toUpperCase());
					languages.add(language);
				} catch (final IllegalArgumentException e) {
					System.err.println("invalid option value for \"-lang\"");
					System.exit(0);
				}
			}
		}

		else {
			for (final LANGUAGE language : LANGUAGE.values()) {
				languages.add(language);
			}
			languages.remove(LANGUAGE.HTML);
			languages.remove(LANGUAGE.JSP);
		}

		return languages;
	}

	public String getSOFTWARE() {
		if (!this.commandLine.hasOption("soft")) {
			System.err.println("option \"soft\" is not specified.");
			System.exit(0);
		}
		return this.commandLine.getOptionValue("soft");
	}

	public String getSource() {
		if (!this.commandLine.hasOption("src")) {
			System.err.println("option \"src\" is not specified.");
			System.exit(0);
		}
		return this.commandLine.getOptionValue("src");
	}

	public boolean hasSIMILARITY() {
		return this.commandLine.hasOption("sml");
	}

	public String getSIMILARITY() {
		if (!this.commandLine.hasOption("sml")) {
			System.err.println("option \"sml\" is not specified.");
			System.exit(0);
		}
		return this.commandLine.getOptionValue("sml");
	}

	public int getTHRESHOLD() {
		return this.commandLine.hasOption("thrld") ? Integer
				.parseInt(this.commandLine.getOptionValue("thrld")) : 50;
	}

	public int getTHREAD() {
		return this.commandLine.hasOption("thd") ? Integer
				.parseInt(this.commandLine.getOptionValue("thd")) : 1;
	}

	public boolean isVERBOSE() {
		return this.commandLine.hasOption("v");
	}

	public boolean hasRESULT() {
		return this.commandLine.hasOption("result");
	}

	public String getRESULT() {
		if (!this.commandLine.hasOption("result")) {
			System.err.println("option \"result\" is not specified.");
			System.exit(0);
		}
		return this.commandLine.getOptionValue("result");
	}

	public boolean isDEBUG() {
		return this.commandLine.hasOption("debug");
	}
}
