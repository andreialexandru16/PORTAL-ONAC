package ro.bithat.dms.service;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

public class SpelParserUtilTest {

	@Test
	public void testUrbanism() {
		String expression = "new Double(context['30826'])<=150?5d:(new Double(context['30826'])<=250?6d:(new Double(context['30826'])<=500?8d:(new Double(context['30826'])<=750?10d:(new Double(context['30826'])<=1000?12d: (14d+(new Double(context['30826'])-1000d)*0.01d)))))";
		
		assertThat(evaluateSpel("150", expression)).isEqualTo(5d, withPrecision(2d));
		assertThat(evaluateSpel("250", expression)).isEqualTo(6d, withPrecision(2d));
		assertThat(evaluateSpel("500", expression)).isEqualTo(8d, withPrecision(2d));
		assertThat(evaluateSpel("750", expression)).isEqualTo(10d, withPrecision(2d));
		assertThat(evaluateSpel("1000", expression)).isEqualTo(12d, withPrecision(2d));
		assertThat(evaluateSpel("1001", expression)).isEqualTo(14d, withPrecision(2d));
		assertThat(evaluateSpel("1100", expression)).isEqualTo(15d, withPrecision(2d));
		assertThat(evaluateSpel("11000", expression)).isEqualTo(114d, withPrecision(2d));
		assertThat(evaluateSpel("21000", expression)).isEqualTo(214d, withPrecision(2d));
	}

	@Test
	public void testNumarCadastral() {
		String expression = "new String(context) matches '.*[A-Za-z].*[A-Za-z].*' and new String(context) matches '.*[0-9].*[0-9].*' and new String(context) matches '[A-Za-z0-9]{4,6}'";
		
		assertThat(evaluateSpelBoolean("aa1234", expression)).isTrue();
		assertThat(evaluateSpelBoolean("a1a234", expression)).isTrue();
		assertThat(evaluateSpelBoolean("1ab234", expression)).isTrue();
		assertThat(evaluateSpelBoolean("1a23b4", expression)).isTrue();
		assertThat(evaluateSpelBoolean("1a234b", expression)).isTrue();
		assertThat(evaluateSpelBoolean("ab123", expression)).isTrue();
		assertThat(evaluateSpelBoolean("ab12", expression)).isTrue();
		
		assertThat(evaluateSpelBoolean("123456", expression)).isFalse();
		assertThat(evaluateSpelBoolean("abcdef", expression)).isFalse();
		assertThat(evaluateSpelBoolean("a12345", expression)).isFalse();
		assertThat(evaluateSpelBoolean("12345a", expression)).isFalse();
		assertThat(evaluateSpelBoolean("12345abc", expression)).isFalse();
		assertThat(evaluateSpelBoolean("a12", expression)).isFalse();
		assertThat(evaluateSpelBoolean("ab2", expression)).isFalse();
		assertThat(evaluateSpelBoolean("a2", expression)).isFalse();
	}
	
	private Double evaluateSpel(String value, String expression) {
		Map<String, String> map = new HashMap<>();
		map.put("30826", value);
		return (Double)SpelParserUtil.parseSpel(map, expression);
	}
	
	private Boolean evaluateSpelBoolean(String value, String expression) {
		return (Boolean)SpelParserUtil.parseSpel(value, expression);
	}
}
