package it.polimi.rest_project.json;

import java.io.IOException;
import java.util.Calendar;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class OptimizedDateSerializer extends JsonSerializer<Calendar> {

	@Override
	public void serialize(Calendar date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		String readableDate;
		Integer month = date.get(2) + 1;
		if (date.get(11) == 0 && date.get(12) == 0 && date.get(13) == 0)
			readableDate = (date.get(5) + "/" + month + "/" + date.get(1));
		else
			readableDate = (date.get(5) + "/" + month + "/" + date.get(1) + " " + date.get(11) + ":" + date.get(12));
		jsonGenerator.writeString(readableDate);
	}

}
