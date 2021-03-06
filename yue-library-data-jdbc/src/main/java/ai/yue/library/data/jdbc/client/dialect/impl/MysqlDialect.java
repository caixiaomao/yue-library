package ai.yue.library.data.jdbc.client.dialect.impl;

import ai.yue.library.base.util.ObjectUtils;
import ai.yue.library.data.jdbc.client.dialect.Dialect;
import ai.yue.library.data.jdbc.client.dialect.DialectNameEnum;
import ai.yue.library.data.jdbc.client.dialect.Wrapper;
import ai.yue.library.data.jdbc.config.properties.JdbcProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

/**
 * MySQL方言
 * 
 * @author	ylyue
 * @since	2020年6月13日
 */
@Slf4j
public class MysqlDialect extends AnsiDialect {
	
	private static final long serialVersionUID = -3734718212043823636L;

	public MysqlDialect(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcProperties jdbcProperties) {
		super(namedParameterJdbcTemplate, jdbcProperties);
		super.wrapper = new Wrapper('`');
		super.dialect = this;
	}

	@Override
	public Dialect cloneDialect() {
		return clone();
	}

	@Override
	public MysqlDialect clone() {
		log.info("执行{}，深度克隆。", getClass());
		DataSource dataSource = ObjectUtils.cloneIfPossible(getNamedParameterJdbcTemplate().getJdbcTemplate().getDataSource());
		return new MysqlDialect(new NamedParameterJdbcTemplate(dataSource), jdbcProperties.clone());
	}

	@Override
	public DialectNameEnum dialectName() {
		return DialectNameEnum.MYSQL;
	}

}
