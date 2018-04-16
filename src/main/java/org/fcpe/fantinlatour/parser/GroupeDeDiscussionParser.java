package org.fcpe.fantinlatour.parser;

import java.io.IOException;
import java.util.List;

public interface GroupeDeDiscussionParser {
	List<String> parse(String filename) throws IOException;
}