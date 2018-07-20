package nl.komtek.moneybird.helper;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class Response {

	int responseCode;
	Optional<String> result;
}
