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
