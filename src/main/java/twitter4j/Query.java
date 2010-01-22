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
package twitter4j;

import twitter4j.http.HttpParameter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A data class represents search query.
 * @see <a href="http://apiwiki.twitter.com/Search-API-Documentation">Twitter API / Search API Documentation</a>
 * @see <a href="http://search.twitter.com/operators">Twitter API / Search Operators</a>
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class Query {
    private String query = null;
    private String lang = null;
    private int rpp = -1;
    private int page = -1;
    private long sinceId = -1;
    private String since = null;
    private String until = null;
    private String geocode = null;
    public Query(){
    }
    public Query(String query){
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    /**
     * Sets the query string
     * @param query the query string
     * @see <a href="http://apiwiki.twitter.com/Search-API-Documentation">Twitter API / Search API Documentation</a>
     * @see <a href="http://search.twitter.com/operators">Twitter API / Search Operators</a>
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Sets the query string
     * @param query the query string
     * @return the instance
     * @see <a href="http://apiwiki.twitter.com/Search-API-Documentation">Twitter API / Search API Documentation</a>
     * @see <a href="http://search.twitter.com/operators">Twitter API / Search Operators</a>
     * @since Twitter4J 2.1.0
     */
    public Query query(String query) {
        setQuery(query);
        return this;
    }

    public String getLang() {
        return lang;
    }

    /**
     * restricts tweets to the given language, given by an <a href="http://en.wikipedia.org/wiki/ISO_639-1">ISO 639-1 code</a>
     * @param lang an <a href="http://en.wikipedia.org/wiki/ISO_639-1">ISO 639-1 code</a>
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * restricts tweets to the given language, given by an <a href="http://en.wikipedia.org/wiki/ISO_639-1">ISO 639-1 code</a>
     * @param lang an <a href="http://en.wikipedia.org/wiki/ISO_639-1">ISO 639-1 code</a>
     * @return the instance
     * @since Twitter4J 2.1.0
     */
    public Query lang(String lang) {
        setLang(lang);
        return this;
    }

    public int getRpp() {
        return rpp;
    }

    /**
     * sets the number of tweets to return per page, up to a max of 100
     * @param rpp the number of tweets to return per page
     */
    public void setRpp(int rpp) {
        this.rpp = rpp;
    }

    /**
     * sets the number of tweets to return per page, up to a max of 100
     * @param rpp the number of tweets to return per page
     * @return the instance
     * @since Twitter4J 2.1.0
     */
    public Query rpp(int rpp) {
        setRpp(rpp);
        return this;
    }

    public int getPage() {
        return page;
    }

    /**
     * sets the page number (starting at 1) to return, up to a max of roughly 1500 results
     * @param page the page number (starting at 1) to return
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * sets the page number (starting at 1) to return, up to a max of roughly 1500 results
     * @param page the page number (starting at 1) to return
     * @return the instance
     * @since Twitter4J 2.1.0
     */
    public Query page(int page) {
        setPage(page);
        return this;
    }

    public long getSinceId() {
        return sinceId;
    }
    
    public String getSince(){
    	return since;
    }

    /**
     * returns tweets with status ids greater than the given id.
     * @param sinceId returns tweets with status ids greater than the given id
     */
    public void setSinceId(long sinceId) {
        this.sinceId = sinceId;
    }

    /**
     * returns tweets tweeted after the given date. 
     * @param date A <code>String</code> in the format yyyy-MM-dd specifying the date.
     */
	public void setSince(String since) {
		this.since=since;
	}
	
	/**
     * returns tweets tweeted after the given date. 
     * @param date A <code>Date</code> specifying the date.
     */
	public void setSince(Date since) {
		this.since=dateFormat.format(since);
	}
	
    /**
     * returns tweets tweeted before the given date. 
     * @param date A <code>String</code> in the format yyyy-MM-dd specifying the date.
     */
	public void setUntil(String until) {
		this.until=until;
	}
	
	/**
     * returns tweets tweeted before the given date. 
     * @param date A <code>Date</code> specifying the date.
     */
	public void setUntil(Date until) {
		this.until=dateFormat.format(until);
	}


    /**
     * returns tweets with status ids greater than the given id.
     * @param sinceId returns tweets with status ids greater than the given id
     * @return the instance
     * @since Twitter4J 2.1.0
     */
    public Query sinceId(long sinceId) {
        setSinceId(sinceId);
        return this;
    }

    public String getGeocode() {
        return geocode;
    }

    public static final String MILES = "mi";
    public static final String KILOMETERS = "km";
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * returns tweets by users located within a given radius of the given latitude/longitude, where the user's location is taken from their Twitter profile
     * @param location geo location
     * @param radius radius
     * @param unit Query.MILES or Query.KILOMETERS
     */
    public void setGeoCode(GeoLocation location, double radius
            , String unit) {
        this.geocode = location.getLatitude() + "," + location.getLongitude() + "," + radius + unit;
    }

    /**
     * returns tweets by users located within a given radius of the given latitude/longitude, where the user's location is taken from their Twitter profile
     * @param location geo location
     * @param radius radius
     * @param unit Query.MILES or Query.KILOMETERS
     * @return the instance
     * @since Twitter4J 2.1.0
     */
    public Query geoCode(GeoLocation location, double radius
            , String unit) {
        setGeoCode(location, radius, unit);
        return this;
    }
    public HttpParameter[] asPostParameters(){
        ArrayList<HttpParameter> params = new ArrayList<HttpParameter>();
        appendParameter("q", query, params);
        appendParameter("lang", lang, params);
        appendParameter("rpp",rpp , params);
        appendParameter("page", page, params);
        appendParameter("since_id",sinceId , params);
        appendParameter("since",since , params);
        appendParameter("until",until , params);
        appendParameter("geocode", geocode, params);
        HttpParameter[] paramArray = new HttpParameter[params.size()];
        return params.toArray(paramArray);
    }

    private void appendParameter(String name, String value, List<HttpParameter> params) {
        if (null != value) {
            params.add(new HttpParameter(name, value));
        }
    }

    private void appendParameter(String name, long value, List<HttpParameter> params) {
        if (0 <= value) {
            params.add(new HttpParameter(name, String.valueOf(value)));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Query query1 = (Query) o;

        if (page != query1.page) return false;
        if (rpp != query1.rpp) return false;
        if (sinceId != query1.sinceId) return false;
        if (geocode != null ? !geocode.equals(query1.geocode) : query1.geocode != null)
            return false;
        if (lang != null ? !lang.equals(query1.lang) : query1.lang != null)
            return false;
        if (query != null ? !query.equals(query1.query) : query1.query != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = query != null ? query.hashCode() : 0;
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        result = 31 * result + rpp;
        result = 31 * result + page;
        result = 31 * result + (int) (sinceId ^ (sinceId >>> 32));
        result = 31 * result + (geocode != null ? geocode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Query{" +
                "query='" + query + '\'' +
                ", lang='" + lang + '\'' +
                ", rpp=" + rpp +
                ", page=" + page +
                ", sinceId=" + sinceId +
                ", geocode='" + geocode + '\'' +
                '}';
    }
}
