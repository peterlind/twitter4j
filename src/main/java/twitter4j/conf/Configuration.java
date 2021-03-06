/*
Copyright (c) 2007-2009, Yusuke Yamamoto
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the Yusuke Yamamoto nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY Yusuke Yamamoto ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL Yusuke Yamamoto BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package twitter4j.conf;

import twitter4j.http.HttpClientConfiguration;
import twitter4j.http.HttpClientWrapperConfiguration;

import java.util.Map;


/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public interface Configuration extends HttpClientConfiguration, HttpClientWrapperConfiguration, java.io.Serializable {

    boolean isDalvik();

    boolean isDebugEnabled();

    String getUserAgent();

    String getSource();

    String getUser();

    String getPassword();

    Map<String, String> getRequestHeaders();

    // methods for HttpClientConfiguration

    String getHttpProxyHost();

    String getHttpProxyUser();

    String getHttpProxyPassword();

    int getHttpProxyPort();

    int getHttpConnectionTimeout();

    int getHttpReadTimeout();

    int getHttpStreamingReadTimeout();

    int getHttpRetryCount();

    int getHttpRetryIntervalSeconds();

    // oauth related setter/getters

    String getOAuthConsumerKey();

    String getOAuthConsumerSecret();

    String getOAuthAccessToken();

    String getOAuthAccessTokenSecret();

    int getAsyncNumThreads();

    String getClientVersion();

    String getClientURL();

    String getRestBaseURL();

    String getSearchBaseURL();

    String getStreamBaseURL();

    String getOAuthRequestTokenURL();

    String getOAuthAuthorizationURL();

    String getOAuthAccessTokenURL();

    String getOAuthAuthenticationURL();

}