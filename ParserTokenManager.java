/* ParserTokenManager.java */
/* Generated By:JavaCC: Do not edit this line. ParserTokenManager.java */
import java.util.*;

/** Token Manager. */
@SuppressWarnings ("unused")
public class ParserTokenManager implements ParserConstants {

  /** Debug output. */
  public static  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public static  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private static final int jjStopStringLiteralDfa_0(int pos, long active0){
   switch (pos)
   {
      case 0:
         if ((active0 & 0xfe00000a21ffe0L) != 0L)
         {
            jjmatchedKind = 59;
            return 6;
         }
         return -1;
      case 1:
         if ((active0 & 0x84000000000000L) != 0L)
            return 6;
         if ((active0 & 0x7a00000a21ffe0L) != 0L)
         {
            jjmatchedKind = 59;
            jjmatchedPos = 1;
            return 6;
         }
         return -1;
      case 2:
         if ((active0 & 0x2000000200460L) != 0L)
            return 6;
         if ((active0 & 0x7800000a01fb80L) != 0L)
         {
            jjmatchedKind = 59;
            jjmatchedPos = 2;
            return 6;
         }
         return -1;
      case 3:
         if ((active0 & 0x800000200c980L) != 0L)
            return 6;
         if ((active0 & 0x70000008013200L) != 0L)
         {
            jjmatchedKind = 59;
            jjmatchedPos = 3;
            return 6;
         }
         return -1;
      case 4:
         if ((active0 & 0x70000008012000L) != 0L)
            return 6;
         if ((active0 & 0x1200L) != 0L)
         {
            if (jjmatchedPos != 4)
            {
               jjmatchedKind = 59;
               jjmatchedPos = 4;
            }
            return 6;
         }
         return -1;
      case 5:
         if ((active0 & 0x1200L) != 0L)
            return 6;
         if ((active0 & 0x40000000000000L) != 0L)
         {
            jjmatchedKind = 59;
            jjmatchedPos = 5;
            return 6;
         }
         return -1;
      default :
         return -1;
   }
}
private static final int jjStartNfa_0(int pos, long active0){
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
static private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
static private int jjMoveStringLiteralDfa0_0(){
   switch(curChar)
   {
      case 33:
         jjmatchedKind = 38;
         return jjMoveStringLiteralDfa1_0(0x400000000000L);
      case 35:
         return jjStopAtPos(0, 24);
      case 38:
         return jjMoveStringLiteralDfa1_0(0x8000000000L);
      case 40:
         return jjStopAtPos(0, 29);
      case 41:
         return jjStopAtPos(0, 30);
      case 42:
         return jjStopAtPos(0, 19);
      case 43:
         return jjStopAtPos(0, 17);
      case 44:
         return jjStopAtPos(0, 37);
      case 45:
         jjmatchedKind = 18;
         return jjMoveStringLiteralDfa1_0(0x200000000000000L);
      case 46:
         return jjStopAtPos(0, 23);
      case 47:
         return jjStopAtPos(0, 28);
      case 58:
         jjmatchedKind = 34;
         return jjMoveStringLiteralDfa1_0(0x1000004100000L);
      case 59:
         jjmatchedKind = 35;
         return jjMoveStringLiteralDfa1_0(0x1000000000L);
      case 60:
         jjmatchedKind = 43;
         return jjMoveStringLiteralDfa1_0(0x200000000000L);
      case 61:
         jjmatchedKind = 33;
         return jjMoveStringLiteralDfa1_0(0x100020000000000L);
      case 62:
         jjmatchedKind = 42;
         return jjMoveStringLiteralDfa1_0(0x100000000000L);
      case 98:
         return jjMoveStringLiteralDfa1_0(0x2000000000080L);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x2000000L);
      case 101:
         return jjMoveStringLiteralDfa1_0(0x8000000000000L);
      case 102:
         return jjMoveStringLiteralDfa1_0(0x80000000010000L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0x4000000000040L);
      case 108:
         return jjMoveStringLiteralDfa1_0(0x820L);
      case 109:
         return jjMoveStringLiteralDfa1_0(0x8000000L);
      case 110:
         return jjMoveStringLiteralDfa1_0(0x200000L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0x60000000000000L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x400L);
      case 115:
         return jjMoveStringLiteralDfa1_0(0x1200L);
      case 116:
         return jjMoveStringLiteralDfa1_0(0xc000L);
      case 117:
         return jjMoveStringLiteralDfa1_0(0x2100L);
      case 119:
         return jjMoveStringLiteralDfa1_0(0x10000000000000L);
      case 123:
         return jjStopAtPos(0, 31);
      case 124:
         jjmatchedKind = 22;
         return jjMoveStringLiteralDfa1_0(0x10000000000L);
      case 125:
         return jjStopAtPos(0, 32);
      case 126:
         return jjStopAtPos(0, 47);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
static private int jjMoveStringLiteralDfa1_0(long active0){
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 38:
         if ((active0 & 0x8000000000L) != 0L)
            return jjStopAtPos(1, 39);
         break;
      case 58:
         if ((active0 & 0x4000000L) != 0L)
            return jjStopAtPos(1, 26);
         break;
      case 59:
         if ((active0 & 0x1000000000L) != 0L)
            return jjStopAtPos(1, 36);
         break;
      case 61:
         if ((active0 & 0x20000000000L) != 0L)
            return jjStopAtPos(1, 41);
         else if ((active0 & 0x100000000000L) != 0L)
            return jjStopAtPos(1, 44);
         else if ((active0 & 0x200000000000L) != 0L)
            return jjStopAtPos(1, 45);
         else if ((active0 & 0x400000000000L) != 0L)
            return jjStopAtPos(1, 46);
         else if ((active0 & 0x1000000000000L) != 0L)
            return jjStopAtPos(1, 48);
         break;
      case 62:
         if ((active0 & 0x100000000000000L) != 0L)
            return jjStopAtPos(1, 56);
         else if ((active0 & 0x200000000000000L) != 0L)
            return jjStopAtPos(1, 57);
         break;
      case 63:
         if ((active0 & 0x100000L) != 0L)
            return jjStopAtPos(1, 20);
         break;
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x8010000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x420L);
      case 102:
         if ((active0 & 0x4000000000000L) != 0L)
            return jjStartNfaWithStates_0(1, 50, 6);
         break;
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x10000000000000L);
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x200800L);
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0x8000000000000L);
      case 110:
         if ((active0 & 0x80000000000000L) != 0L)
            return jjStartNfaWithStates_0(1, 55, 6);
         return jjMoveStringLiteralDfa2_0(active0, 0x2140L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000002000080L);
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0x60000000004000L);
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x1200L);
      case 121:
         return jjMoveStringLiteralDfa2_0(active0, 0x8000L);
      case 124:
         if ((active0 & 0x10000000000L) != 0L)
            return jjStopAtPos(1, 40);
         break;
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
static private int jjMoveStringLiteralDfa2_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 102:
         if ((active0 & 0x400L) != 0L)
            return jjStartNfaWithStates_0(2, 10, 6);
         break;
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x70000000002100L);
      case 108:
         if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(2, 21, 6);
         return jjMoveStringLiteralDfa3_0(active0, 0x10000L);
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000000L);
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x80L);
      case 112:
         return jjMoveStringLiteralDfa3_0(active0, 0x8000L);
      case 114:
         return jjMoveStringLiteralDfa3_0(active0, 0x1200L);
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x8000000000800L);
      case 116:
         if ((active0 & 0x20L) != 0L)
            return jjStartNfaWithStates_0(2, 5, 6);
         else if ((active0 & 0x40L) != 0L)
            return jjStartNfaWithStates_0(2, 6, 6);
         return jjMoveStringLiteralDfa3_0(active0, 0x8000000L);
      case 117:
         return jjMoveStringLiteralDfa3_0(active0, 0x4000L);
      case 120:
         if ((active0 & 0x2000000000000L) != 0L)
            return jjStartNfaWithStates_0(2, 49, 6);
         break;
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
static private int jjMoveStringLiteralDfa3_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 99:
         return jjMoveStringLiteralDfa4_0(active0, 0x8000000L);
      case 101:
         if ((active0 & 0x4000L) != 0L)
            return jjStartNfaWithStates_0(3, 14, 6);
         else if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(3, 15, 6);
         else if ((active0 & 0x8000000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 51, 6);
         break;
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x200L);
      case 108:
         if ((active0 & 0x80L) != 0L)
            return jjStartNfaWithStates_0(3, 7, 6);
         return jjMoveStringLiteralDfa4_0(active0, 0x10000000000000L);
      case 110:
         return jjMoveStringLiteralDfa4_0(active0, 0x60000000000000L);
      case 111:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000L);
      case 115:
         if ((active0 & 0x2000000L) != 0L)
            return jjStartNfaWithStates_0(3, 25, 6);
         return jjMoveStringLiteralDfa4_0(active0, 0x10000L);
      case 116:
         if ((active0 & 0x100L) != 0L)
            return jjStartNfaWithStates_0(3, 8, 6);
         else if ((active0 & 0x800L) != 0L)
            return jjStartNfaWithStates_0(3, 11, 6);
         break;
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x1000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
static private int jjMoveStringLiteralDfa4_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 99:
         return jjMoveStringLiteralDfa5_0(active0, 0x1000L);
      case 101:
         if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(4, 16, 6);
         else if ((active0 & 0x10000000000000L) != 0L)
            return jjStartNfaWithStates_0(4, 52, 6);
         break;
      case 104:
         if ((active0 & 0x8000000L) != 0L)
            return jjStartNfaWithStates_0(4, 27, 6);
         break;
      case 110:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(4, 13, 6);
         return jjMoveStringLiteralDfa5_0(active0, 0x200L);
      case 116:
         if ((active0 & 0x20000000000000L) != 0L)
         {
            jjmatchedKind = 53;
            jjmatchedPos = 4;
         }
         return jjMoveStringLiteralDfa5_0(active0, 0x40000000000000L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
static private int jjMoveStringLiteralDfa5_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 103:
         if ((active0 & 0x200L) != 0L)
            return jjStartNfaWithStates_0(5, 9, 6);
         break;
      case 108:
         return jjMoveStringLiteralDfa6_0(active0, 0x40000000000000L);
      case 116:
         if ((active0 & 0x1000L) != 0L)
            return jjStartNfaWithStates_0(5, 12, 6);
         break;
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
static private int jjMoveStringLiteralDfa6_0(long old0, long active0){
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 110:
         if ((active0 & 0x40000000000000L) != 0L)
            return jjStartNfaWithStates_0(6, 54, 6);
         break;
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
static private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
static private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 8;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 60)
                        kind = 60;
                     { jjCheckNAdd(7); }
                  }
                  else if (curChar == 34)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 1:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 3:
                  if (curChar == 34)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 4:
                  if (curChar == 34 && kind > 58)
                     kind = 58;
                  break;
               case 6:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 59)
                     kind = 59;
                  jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 7:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 60)
                     kind = 60;
                  { jjCheckNAdd(7); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
               case 6:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 59)
                     kind = 59;
                  { jjCheckNAdd(6); }
                  break;
               case 1:
                  if ((0xffffffffefffffffL & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 2:
                  if (curChar == 92)
                     jjstateSet[jjnewStateCnt++] = 3;
                  break;
               case 3:
                  if ((0x14404410000000L & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 1:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     { jjAddStates(0, 2); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 8 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, "\154\145\164", "\151\156\164", 
"\142\157\157\154", "\165\156\151\164", "\163\164\162\151\156\147", "\162\145\146", 
"\154\151\163\164", "\163\164\162\165\143\164", "\165\156\151\157\156", "\164\162\165\145", 
"\164\171\160\145", "\146\141\154\163\145", "\53", "\55", "\52", "\72\77", "\156\151\154", "\174", 
"\56", "\43", "\143\157\156\163", "\72\72", "\155\141\164\143\150", "\57", "\50", 
"\51", "\173", "\175", "\75", "\72", "\73", "\73\73", "\54", "\41", "\46\46", 
"\174\174", "\75\75", "\76", "\74", "\76\75", "\74\75", "\41\75", "\176", "\72\75", 
"\142\157\170", "\151\146", "\145\154\163\145", "\167\150\151\154\145", 
"\160\162\151\156\164", "\160\162\151\156\164\154\156", "\146\156", "\75\76", "\55\76", null, null, 
null, };
static protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}
static final int[] jjnextStates = {
   1, 2, 4, 
};

static int curLexState = 0;
static int defaultLexState = 0;
static int jjnewStateCnt;
static int jjround;
static int jjmatchedPos;
static int jjmatchedKind;

/** Get the next Token. */
public static Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(Exception e)
   {
      jjmatchedKind = 0;
      jjmatchedPos = -1;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

static void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
static void MoreLexicalActions()
{
   jjimageLen += (lengthOfMatch = jjmatchedPos + 1);
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
static void TokenLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
static private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
static private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
static private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

static private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

    /** Constructor. */
    public ParserTokenManager(SimpleCharStream stream){

      if (input_stream != null)
        throw new TokenMgrError("ERROR: Second call to constructor of static lexer. You must use ReInit() to initialize the static variables.", TokenMgrError.STATIC_LEXER_ERROR);

    input_stream = stream;
  }

  /** Constructor. */
  public ParserTokenManager (SimpleCharStream stream, int lexState){
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Reinitialise parser. */
  
  static public void ReInit(SimpleCharStream stream)
  {


    jjmatchedPos =
    jjnewStateCnt =
    0;
    curLexState = defaultLexState;
    input_stream = stream;
    ReInitRounds();
  }

  static private void ReInitRounds()
  {
    int i;
    jjround = 0x80000001;
    for (i = 8; i-- > 0;)
      jjrounds[i] = 0x80000000;
  }

  /** Reinitialise parser. */
  static public void ReInit(SimpleCharStream stream, int lexState)
  
  {
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Switch to specified lex state. */
  public static void SwitchTo(int lexState)
  {
    if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
    else
      curLexState = lexState;
  }


/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0x1fffffffffffffe1L, 
};
static final long[] jjtoSkip = {
   0x1eL, 
};
static final long[] jjtoSpecial = {
   0x0L, 
};
static final long[] jjtoMore = {
   0x0L, 
};
    static protected SimpleCharStream  input_stream;

    static private final int[] jjrounds = new int[8];
    static private final int[] jjstateSet = new int[2 * 8];
    private static final StringBuilder jjimage = new StringBuilder();
    private static StringBuilder image = jjimage;
    private static int jjimageLen;
    private static int lengthOfMatch;
    static protected int curChar;
}
