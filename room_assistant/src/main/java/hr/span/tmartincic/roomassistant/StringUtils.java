package hr.span.tmartincic.roomassistant;

import hr.span.tmartincic.roomassistant.models.request.attributes.InvalidAttributeNameFormatException;

/**
 * Created by tmartincic on 6/23/2015.
 */
public class StringUtils
{
    //todo handle request, response
    private static final String[] VALID_LITERALS = new String[]{
            "type",
            "attribute",
            "message"
    };

    /* Original code:
    public static String strip(String text, String searchString, String replacement, int max)
  {
    if ((isEmpty(text)) || (isEmpty(searchString)) || (replacement == null) || (max == 0)) {
      return text;
    }
    int start = 0;
    int end = text.indexOf(searchString, start);
    if (end == -1) {
      return text;
    }
    int replLength = searchString.length();
    int increase = replacement.length() - replLength;
    increase = increase < 0 ? 0 : increase;
    increase *= (max > 64 ? 64 : max < 0 ? 16 : max);
    StringBuilder buf = new StringBuilder(text.length() + increase);
    while (end != -1)
    {
      buf.append(text.substring(start, end)).append(replacement);
      start = end + replLength;
      max--;
      if (max == 0) {
        break;
      }
      end = text.indexOf(searchString, start);
    }
    buf.append(text.substring(start));
    return buf.toString();
  }
    * */

    //TODO refactor message and type types with prefixes
    /** Strips any occurrence of literals in VALID_LITERALS from given string.
     *  Situation with multiple occurrences of different literals is not tested. */
    public static String strip(String text) throws InvalidAttributeNameFormatException
    {
        int max = -1;
        //Check if the given name is empty
        if ((isEmpty(text))) {
            return text;
        }

        //Check if the given name contains one of the valid literal values
        boolean hasLiteral = false;
        String searchString = null;
        for(String literal : VALID_LITERALS)
        {
            hasLiteral = contains(text, literal);
            if(hasLiteral)
            {
                searchString = literal;
                break;
            }
        }
        if(!hasLiteral) throw new InvalidAttributeNameFormatException("Class name does not contain");

        //Check if the literal value is at the start of given name
        boolean startsWithLiteral = text.toLowerCase().startsWith(searchString);
        if(!startsWithLiteral) throw new InvalidAttributeNameFormatException(
                String.format("Class name does not start with %s, %s or %s", VALID_LITERALS[0], VALID_LITERALS[1], VALID_LITERALS[2]));


        int start = 0;
        int end = text.toLowerCase().indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = "".length() - replLength;
        increase = increase < 0 ? 0 : increase;
        increase *= (16);
        StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != -1)
        {
            buf.append(text.substring(start, end)).append("");
            start = end + replLength;
            max--;
            if (max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    public static boolean isEmpty(CharSequence cs)
    {
        return (cs == null) || (cs.length() == 0);
    }

    public static boolean contains(CharSequence seq, CharSequence searchSeq)
    {
        if ((seq == null) || (searchSeq == null)) {
            return false;
        }
        return indexOf(seq, searchSeq, 0) >= 0;
    }

    static int indexOf(CharSequence cs, CharSequence searchChar, int start)
    {
        return cs.toString().toLowerCase().indexOf(searchChar.toString(), start);
    }
}
