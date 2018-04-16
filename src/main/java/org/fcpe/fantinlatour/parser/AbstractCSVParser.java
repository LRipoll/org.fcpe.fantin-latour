package org.fcpe.fantinlatour.parser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;

public abstract class AbstractCSVParser<T> {

	@SuppressWarnings("deprecation")
	protected static final String capitalize(String chaine) {
		return StringUtils.capitaliseAllWords(StringUtils.lowerCase(StringUtils.trim(chaine.replace("-", " "))));

	}

	public AbstractCSVParser() {
		super();

	}

	public List<T> parse(String filename) throws IOException {
		Reader in = new FileReader(filename);
		return parse(in);

	}
	
	public List<T> parse(Reader reader) throws IOException {
		List<T> result = new ArrayList<T>();
	
		CSVFormat cvsFormat = getCVSFormat();
		Iterable<CSVRecord> records = cvsFormat.parse(reader);
		for (CSVRecord record : records) {
			T parsedValue = parse(record);
			if (parsedValue!=null)
				result.add(parsedValue);
		}
		return result;

	}

	protected CSVFormat getCVSFormat() {
		return CSVFormat.DEFAULT.withDelimiter(getDelimiter());
	}


	protected char getDelimiter() {
		return ';';
	}

	protected abstract T parse(CSVRecord record);

}