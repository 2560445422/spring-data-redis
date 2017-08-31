/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.redis.connection.lettuce;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;

import java.time.Duration;
import java.util.Optional;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.lettuce.DefaultLettuceClientConfiguration.DefaultLettuceClientConfigurationBuilder;

/**
 * Default implementation of {@literal LettucePoolingClientConfiguration}.
 *
 * @author Mark Paluch
 * @since 2.0
 */
class DefaultLettucePoolingClientConfiguration implements LettuceClientConfiguration.LettucePoolingClientConfiguration {

	@Override
	public boolean isUseSsl() {
		return clientConfiguration.isUseSsl();
	}

	private final LettuceClientConfiguration clientConfiguration;
	private final GenericObjectPoolConfig poolConfig;

	DefaultLettucePoolingClientConfiguration(LettuceClientConfiguration clientConfiguration,
			GenericObjectPoolConfig poolConfig) {

		this.clientConfiguration = clientConfiguration;
		this.poolConfig = poolConfig;
	}

	/* (non-Javadoc)
	 * @see org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration#isUsePooling()
	 */
	@Override
	public boolean isUsePooling() {
		return true;
	}

	@Override
	public boolean isVerifyPeer() {
		return clientConfiguration.isVerifyPeer();
	}

	@Override
	public boolean isStartTls() {
		return clientConfiguration.isStartTls();
	}

	@Override
	public Optional<ClientResources> getClientResources() {
		return clientConfiguration.getClientResources();
	}

	@Override
	public Optional<ClientOptions> getClientOptions() {
		return clientConfiguration.getClientOptions();
	}

	@Override
	public Duration getCommandTimeout() {
		return clientConfiguration.getCommandTimeout();
	}

	@Override
	public Duration getShutdownTimeout() {
		return clientConfiguration.getShutdownTimeout();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration.LettucePoolingClientConfiguration#getPoolConfig()
	 */
	@Override
	public GenericObjectPoolConfig getPoolConfig() {
		return poolConfig;
	}

	static class DefaultLettucePoolingClientConfigurationBuilder
			implements LettucePoolingClientConfigurationBuilder, LettuceSslClientConfigurationBuilder {

		final DefaultLettuceClientConfigurationBuilder delegate;
		GenericObjectPoolConfig poolConfig;

		public DefaultLettucePoolingClientConfigurationBuilder(DefaultLettuceClientConfigurationBuilder delegate) {
			this.delegate = delegate;
		}

		public LettuceSslClientConfigurationBuilder useSsl() {

			delegate.useSsl();
			return this;
		}

		public LettucePoolingClientConfigurationBuilder clientResources(ClientResources clientResources) {

			delegate.clientResources(clientResources);
			return this;
		}

		public LettucePoolingClientConfigurationBuilder clientOptions(ClientOptions clientOptions) {

			delegate.clientOptions(clientOptions);
			return this;
		}

		public LettucePoolingClientConfigurationBuilder commandTimeout(Duration timeout) {

			delegate.commandTimeout(timeout);
			return this;
		}

		public LettucePoolingClientConfigurationBuilder shutdownTimeout(Duration shutdownTimeout) {
			delegate.shutdownTimeout(shutdownTimeout);
			return this;
		}

		@Override
		public DefaultLettucePoolingClientConfigurationBuilder withConnectionPooling(GenericObjectPoolConfig poolConfig) {

			this.poolConfig = poolConfig;
			return this;
		}

		@Override
		public LettuceSslClientConfigurationBuilder disablePeerVerification() {

			delegate.disablePeerVerification();
			return this;
		}

		@Override
		public LettuceSslClientConfigurationBuilder startTls() {

			delegate.startTls();
			return this;
		}

		@Override
		public LettuceClientConfigurationBuilder and() {

			delegate.and();
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration.LettuceClientConfigurationBuilder#build()
		 */
		@Override
		public LettucePoolingClientConfiguration build() {
			return new DefaultLettucePoolingClientConfiguration(delegate.build(), poolConfig);
		}
	}
}
