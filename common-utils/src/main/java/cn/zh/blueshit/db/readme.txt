service上面
            @DoRoute(routeField="id")
            public int save(SignRecordEntity signRecordEntity) {
                if (signRecordEntity==null){
                    throw new IllegalArgumentException("param illegal");
                }
                return signRecordMapper.insert(signRecordEntity);
            }

po,vo全部继承baseObject , PaymentSuccEntity extends BaseObject

mappser里面
<select id="selectSuccByPayId" resultType="PaymentSuccEntity" parameterType="PaymentSuccQueryReq">
		select
		<include refid="queryAllColumns" />
		from payment_succ${tableIndex}
		where pay_id = #{payId}
</select>

/* <bean id="dBRouter" class="cn.zh.blueshit.db.DefaultDbRouterImpl">
       <property name="dbRuleSetList">
       <!-- 标识符类型 -->
       <list>
       <ref bean="dbRuleSet" />
       </list>
       </property>
       </bean>
       <bean id="dbRuleSet" class="cn.zh.blueshit.db.bean.DbRuleSet">
       <property name="ruleType" value="3"></property>
       <property name="dbNumber" value="3"></property>
       <property name="routeType" value="2"></property>
       <property name="tableNumber" value="2"></property>
       <property name="dbKeyArray">
       <list>
       <value>db0</value>
       <value>db1</value>
       <value>db2</value>
       </list>
       </property>
       </bean>*/