package ro.bithat.dms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Map;

public class SpelParserUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SpelParserUtil.class);
	
	public static Object parseSpel(Object context, String expression) {
		Object localContext = context;
//		if (context instanceof Map) {
			localContext = new SpelContext(context);
//		}
		ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext simpleContext = new StandardEvaluationContext(localContext);

        try {
            Expression exp = parser.parseExpression(expression);
            Object result = exp.getValue(simpleContext);

            return result;
        } catch (Throwable th) {
            logger.warn("Error trying to parse java formula  =" + expression);

            if (th.getMessage() != null) {
                logger.warn(th.getMessage());
            }
            
            return null;
        }

	}
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("30826", "11000");
		System.out.println(parseSpel(map, "new Double(context['30826'])<=150?6:(new Double(context['30826'])<=250?7:(new Double(context['30826'])<=500?9:(new Double(context['30826'])<=750?12:(new Double(context['30826'])<=1000?14: (14+(new Double(context['30826'])-1000)*0.01))))) "));
	}
}
