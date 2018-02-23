package cn.blueshit.cn.test.sql;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.*;

/**
 * Created by wangjinsi on 2015-09-24.
 */
public abstract class AbstractExpressionVisitor implements ExpressionVisitor {
  @Override
  public void visit(NullValue nullValue) {

  }

  @Override
  public void visit(Function function) {
    if (function.getParameters() != null) {
      for (Expression expression : function.getParameters().getExpressions()) {
        expression.accept(this);
      }
    }
  }

  @Override
  public void visit(SignedExpression signedExpression) {
    signedExpression.getExpression().accept(this);
  }

  @Override
  public void visit(JdbcParameter jdbcParameter) {

  }

  @Override
  public void visit(JdbcNamedParameter jdbcNamedParameter) {

  }

  @Override
  public void visit(DoubleValue doubleValue) {

  }

  @Override
  public void visit(LongValue longValue) {
  }

  @Override
  public void visit(DateValue dateValue) {

  }

  @Override
  public void visit(TimeValue timeValue) {

  }

  @Override
  public void visit(TimestampValue timestampValue) {

  }

  @Override
  public void visit(Parenthesis parenthesis) {
    parenthesis.getExpression().accept(this);
  }

  @Override
  public void visit(StringValue stringValue) {

  }

  @Override
  public void visit(Addition addition) {

  }

  @Override
  public void visit(Division division) {

  }

  @Override
  public void visit(Multiplication multiplication) {

  }

  @Override
  public void visit(Subtraction subtraction) {

  }

  @Override
  public void visit(AndExpression andExpression) {
    andExpression.getLeftExpression().accept(this);
    andExpression.getRightExpression().accept(this);
  }

  @Override
  public void visit(OrExpression orExpression) {
    orExpression.getLeftExpression().accept(this);
    orExpression.getRightExpression().accept(this);
  }

  @Override
  public void visit(Between between) {
    between.getLeftExpression().accept(this);
    between.getBetweenExpressionStart().accept(this);
    between.getBetweenExpressionEnd().accept(this);
  }

  @Override
  public void visit(EqualsTo equalsTo) {
    equalsTo.getLeftExpression().accept(this);
    equalsTo.getRightExpression().accept(this);
  }

  @Override
  public void visit(GreaterThan greaterThan) {

  }

  @Override
  public void visit(GreaterThanEquals greaterThanEquals) {

  }

  @Override
  public void visit(InExpression inExpression) {
    inExpression.getLeftExpression().accept(this);
    final ExpressionVisitor expressionVisitor = this;
    if (inExpression.getLeftItemsList() != null) {
      inExpression.getLeftItemsList().accept(new ItemsListVisitor() {
        @Override
        public void visit(SubSelect subSelect) {
          expressionVisitor.visit(subSelect);
        }

        @Override
        public void visit(ExpressionList expressionList) {
          if (expressionList.getExpressions() != null) {
            for (Expression expression : expressionList.getExpressions()) {
              expression.accept(expressionVisitor);
            }
          }
        }

        @Override
        public void visit(MultiExpressionList multiExprList) {
          if (multiExprList.getExprList() != null) {
            for (ExpressionList expressionList : multiExprList.getExprList()) {
              for (Expression expression : expressionList.getExpressions()) {
                expression.accept(expressionVisitor);
              }
            }
          }
        }
      });
    }
    if (inExpression.getRightItemsList() != null) {
      inExpression.getRightItemsList().accept(new ItemsListVisitor() {
        @Override
        public void visit(SubSelect subSelect) {
          expressionVisitor.visit(subSelect);
        }

        @Override
        public void visit(ExpressionList expressionList) {
          if (expressionList.getExpressions() != null) {
            for (Expression expression : expressionList.getExpressions()) {
              expression.accept(expressionVisitor);
            }
          }
        }

        @Override
        public void visit(MultiExpressionList multiExprList) {
          if (multiExprList.getExprList() != null) {
            for (ExpressionList expressionList : multiExprList.getExprList()) {
              for (Expression expression : expressionList.getExpressions()) {
                expression.accept(expressionVisitor);
              }
            }
          }
        }
      });
    }
  }

  @Override
  public void visit(IsNullExpression isNullExpression) {
    isNullExpression.getLeftExpression().accept(this);

  }

  @Override
  public void visit(LikeExpression likeExpression) {
    likeExpression.getLeftExpression().accept(this);
    likeExpression.getRightExpression().accept(this);
  }

  @Override
  public void visit(MinorThan minorThan) {

  }

  @Override
  public void visit(MinorThanEquals minorThanEquals) {

  }

  @Override
  public void visit(NotEqualsTo notEqualsTo) {

  }

  @Override
  public void visit(Column tableColumn) {

  }

  @Override
  public void visit(SubSelect subSelect) {
    final ExpressionVisitor expressionVisitor = this;
    subSelect.getSelectBody().accept(new SelectVisitor() {
      @Override
      public void visit(PlainSelect plainSelect) {
        plainSelect.getWhere().accept(expressionVisitor);
      }

      @Override
      public void visit(SetOperationList setOpList) {
        throw new UnsupportedOperationException("不支持此方法");
      }

      @Override
      public void visit(WithItem withItem) {
        throw new UnsupportedOperationException("不支持此方法");
      }
    });
  }

  @Override
  public void visit(CaseExpression caseExpression) {
    if (caseExpression.getWhenClauses() != null) {
      for (Expression expression : caseExpression.getWhenClauses()) {
        expression.accept(this);
      }
    }
    caseExpression.getElseExpression().accept(this);
    caseExpression.getSwitchExpression().accept(this);
  }

  @Override
  public void visit(WhenClause whenClause) {
    whenClause.getWhenExpression().accept(this);
    whenClause.getThenExpression().accept(this);
  }

  @Override
  public void visit(ExistsExpression existsExpression) {
    existsExpression.getRightExpression().accept(this);
  }

  @Override
  public void visit(AllComparisonExpression allComparisonExpression) {
    visit(allComparisonExpression.getSubSelect());
  }

  @Override
  public void visit(AnyComparisonExpression anyComparisonExpression) {
    visit(anyComparisonExpression.getSubSelect());
  }

  @Override
  public void visit(Concat concat) {

  }

  @Override
  public void visit(Matches matches) {

  }

  @Override
  public void visit(BitwiseAnd bitwiseAnd) {

  }

  @Override
  public void visit(BitwiseOr bitwiseOr) {

  }

  @Override
  public void visit(BitwiseXor bitwiseXor) {

  }

  @Override
  public void visit(CastExpression cast) {

  }

  @Override
  public void visit(Modulo modulo) {

  }

  @Override
  public void visit(AnalyticExpression aexpr) {

  }

  @Override
  public void visit(ExtractExpression eexpr) {

  }

  @Override
  public void visit(IntervalExpression iexpr) {

  }

  @Override
  public void visit(OracleHierarchicalExpression oexpr) {
    oexpr.getStartExpression().accept(this);
    oexpr.getConnectExpression().accept(this);
  }

  @Override
  public void visit(RegExpMatchOperator rexpr) {

  }

  @Override
  public void visit(HexValue hexValue) {

  }

  @Override
  public void visit(WithinGroupExpression withinGroupExpression) {

  }

  @Override
  public void visit(JsonExpression jsonExpression) {

  }

  @Override
  public void visit(RegExpMySQLOperator regExpMySQLOperator) {

  }

  @Override
  public void visit(UserVariable userVariable) {

  }

  @Override
  public void visit(NumericBind numericBind) {

  }

  @Override
  public void visit(KeepExpression keepExpression) {

  }

  @Override
  public void visit(MySQLGroupConcat mySQLGroupConcat) {

  }

  @Override
  public void visit(RowConstructor rowConstructor) {

  }
}
