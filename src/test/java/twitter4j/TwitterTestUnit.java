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

import java.io.File;
import java.util.Date;
import java.util.List;
import static twitter4j.DAOTest.*;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public class TwitterTestUnit extends TwitterTestBase {

    public TwitterTestUnit(String name) {
        super(name);
    }


    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetPublicTimeline() throws Exception {
        List<Status> statuses;
        statuses = twitterAPI1.getPublicTimeline();
        assertTrue("size", 5 < statuses.size());
    }

    public void testGetHomeTimeline() throws Exception {
        List<Status> status = twitterAPI1.getHomeTimeline();
        assertTrue(0 < status.size());
    }
    public void testSerializability() throws Exception {
        Twitter twitter = new TwitterFactory().getBasicAuthorizedInstance("foo", "bar");
        Twitter deserialized = (Twitter)assertDeserializedFormIsEqual(twitter);

        assertEquals(deserialized.screenName, twitter.screenName);
        assertEquals(deserialized.auth, twitter.auth);

        twitter = new TwitterFactory().getInstance();
        deserialized = (Twitter)assertDeserializedFormIsEqual(twitter);
        assertEquals(deserialized.screenName, twitter.screenName);
        assertEquals(deserialized.auth, twitter.auth);

    }

    public void testGetFriendsTimeline() throws Exception {
        List<Status> actualReturn;

        actualReturn = twitterAPI1.getFriendsTimeline();
        assertTrue(actualReturn.size() > 0);
        actualReturn = twitterAPI1.getFriendsTimeline(new Paging(1l));
        assertTrue(actualReturn.size() > 0);
        //this is necessary because the twitter server's clock tends to delay
        actualReturn = twitterAPI1.getFriendsTimeline(new Paging(1000l));
        assertTrue(actualReturn.size() > 0);

        actualReturn = twitterAPI1.getFriendsTimeline(new Paging(1));
        assertTrue(actualReturn.size() > 0);
    }

    public void testShowUser() throws Exception {
        User user = twitterAPI1.showUser(id1.name);
        assertEquals(id1.name, user.getScreenName());
        assertNotNull(user.getLocation());
        assertNotNull(user.getDescription());
        assertNotNull(user.getProfileImageURL());
        assertNotNull(user.getURL());
        assertFalse(user.isProtected());

        assertTrue(0 <= user.getFavouritesCount());
        assertTrue(0 <= user.getFollowersCount());
        assertTrue(0 <= user.getFriendsCount());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getTimeZone());
        assertNotNull(user.getProfileBackgroundImageUrl());

        assertTrue(0 <= user.getStatusesCount());
        assertNotNull(user.getProfileBackgroundColor());
        assertNotNull(user.getProfileTextColor());
        assertNotNull(user.getProfileLinkColor());
        assertNotNull(user.getProfileSidebarBorderColor());
        assertNotNull(user.getProfileSidebarFillColor());
        assertNotNull(user.getProfileTextColor());

        assertTrue(1 < user.getFollowersCount());
        assertNotNull(user.getStatusCreatedAt());
        assertNotNull(user.getStatusText());
        assertNotNull(user.getStatusSource());
        assertFalse(user.isStatusFavorited());
        assertEquals(-1, user.getStatusInReplyToStatusId());
        assertEquals(-1, user.getStatusInReplyToUserId());
        assertFalse(user.isStatusFavorited());
        assertNull(user.getStatusInReplyToScreenName());

        //test case for TFJ-91 null pointer exception getting user detail on users with no statuses
        //http://yusuke.homeip.net/jira/browse/TFJ-91
        unauthenticated.showUser("twit4jnoupdate");
        twitterAPI1.showUser("tigertest");

        user = unauthenticated.showUser(numberId);
        assertEquals(numberIdId, user.getId());

        user = unauthenticated.showUser(numberIdId);
        assertEquals(numberIdId, user.getId());
    }

    // list deletion doesn't work now.
    // http://groups.google.com/group/twitter-development-talk/t/4e4164a347da1c3b
    // http://code.google.com/p/twitter-api/issues/detail?id=1327
//    public void testList() throws Exception {
//        PagableResponseList<UserList> userLists;
//        userLists = twitterAPI1.getUserLists(id1.name,-1l);
//        for(UserList alist : userLists){
//            twitterAPI1.destroyUserList(alist.getId());
//        }
//
//        /*List Methods*/
//        UserList userList;
//        //ensuring createUserList works in the case an email is specified a userid
//        userList = twitterAPI3.createUserList("api3 is email", false, null);
//        twitterAPI3.destroyUserList(userList.getId());
//        userList = twitterAPI1.createUserList("testpoint1", false, "description1");
//        assertNotNull(userList);
//        assertEquals("testpoint1", userList.getName());
//        assertEquals("description1", userList.getDescription());
//
//        userList = twitterAPI1.updateUserList(userList.getId(), "testpoint2", true, "description2");
//        assertNotNull(userList);
//        assertEquals("testpoint2", userList.getName());
//        assertEquals("description2", userList.getDescription());
//
//
//        userLists = twitterAPI1.getUserLists(id1.name, -1l);
//        assertFalse(userLists.size() == 0);
//
//        userList = twitterAPI1.showUserList(id1.name, userList.getId());
//        assertNotNull(userList);
//
//        List<Status> statuses = twitterAPI1.getUserListStatuses(id1.name, userList.getId(), new Paging());
//        assertNotNull(statuses);
//
//        /*List Member Methods*/
//        User user;
//        try {
//            user = twitterAPI1.checkUserListMembership(id1.name, id2.id, userList.getId());
//            fail("id2 shouldn't be a member of the userList yet. expecting a TwitterException");
//        } catch (TwitterException ignore) {
//            assertEquals(404, ignore.getStatusCode());
//        }
//        userList = twitterAPI1.addUserListMember(userList.getId(), id2.id);
//        userList = twitterAPI1.addUserListMember(userList.getId(), id4.id);
//        assertNotNull(userList);
//
//        List<User> users = twitterAPI1.getUserListMembers(id1.name, userList.getId(), -1);
//        // workaround issue 1301
//        // http://code.google.com/p/twitter-api/issues/detail?id=1301
////        assertEquals(userList.getMemberCount(), users.size());
//        assertTrue(0 < users.size());// workaround issue 1301
//
//        userList = twitterAPI1.deleteUserListMember(userList.getId(), id2.id);
//        assertNotNull(userList);
//        //
////        assertEquals(1, userList.getMemberCount());
//
//        user = twitterAPI1.checkUserListMembership(id1.name, userList.getId(), id4.id);
//        assertEquals(id4.id, user.getId());
//
//        userLists = twitterAPI1.getUserListMemberships(id1.name, -1l);
//        assertNotNull(userLists);
//
//        userLists = twitterAPI1.getUserListSubscriptions(id1.name, -1l);
//        assertNotNull(userLists);
//        assertEquals(0, userLists.size());
//
//        /*List Subscribers Methods*/
//
//        users = twitterAPI1.getUserListSubscribers(id1.name, userList.getId(), -1);
//        assertEquals(0, users.size());
//        try {
//            twitterAPI2.subscribeUserList(id1.name, userList.getId());
//        } catch (TwitterException te) {
//            // workarounding issue 1300
//            // http://code.google.com/p/twitter-api/issues/detail?id=1300
//            assertEquals(404,te.getStatusCode());
//        }
//        // expected subscribers: id2
//        try {
//            twitterAPI4.subscribeUserList(id1.name, userList.getId());
//        } catch (TwitterException te) {
//            // workarounding issue 1300
//            assertEquals(404, te.getStatusCode());
//        }
//        // expected subscribers: id2 and id4
//        try {
//            twitterAPI2.unsubscribeUserList(id1.name, userList.getId());
//        } catch (TwitterException te) {
//            // workarounding issue 1300
//            assertEquals(404, te.getStatusCode());
//        }
//        // expected subscribers: id4
//        users = twitterAPI1.getUserListSubscribers(id1.name, userList.getId(), -1);
////        assertEquals(1, users.size()); //only id4 should be subscribing the userList
//        assertTrue(0 <= users.size()); // workarounding issue 1300
//        try {
//        user = twitterAPI1.checkUserListSubscription(id1.name, userList.getId(), id4.id);
//        assertEquals(id4.id, user.getId());
//        } catch (TwitterException te) {
//            // workarounding issue 1300
//            assertEquals(404, te.getStatusCode());
//        }
//
//        userLists = twitterAPI1.getUserListSubscriptions(id4.name, -1l);
//        assertNotNull(userLists);
////        assertEquals(1, userLists.size()); workarounding issue 1300
//
//        try {
//            user = twitterAPI1.checkUserListSubscription(id1.name, id2.id, userList.getId());
//            fail("id2 shouldn't be a subscriber the userList. expecting a TwitterException");
//        } catch (TwitterException ignore) {
//            assertEquals(404, ignore.getStatusCode());
//        }
//
//        userList = twitterAPI1.destroyUserList(userList.getId());
//        assertNotNull(userList);
//    }

    public void testUserTimeline() throws Exception {
        List<Status> statuses;
        statuses = twitterAPI1.getUserTimeline();
        assertTrue("size", 0 < statuses.size());
        statuses = unauthenticated.getUserTimeline("1000");
        assertTrue("size", 0 < statuses.size());
        assertEquals(9737332,statuses.get(0).getUser().getId());
        statuses = unauthenticated.getUserTimeline(1000);
        assertTrue("size", 0 < statuses.size());
        assertEquals(1000,statuses.get(0).getUser().getId());

        statuses = twitterAPI1.getUserTimeline(new Paging(999383469l));
        assertTrue("size", 0 < statuses.size());
        statuses = unauthenticated.getUserTimeline(id1.name, new Paging().count(10));
        assertTrue("size", 0 < statuses.size());
        statuses = twitterAPI1.getUserTimeline(new Paging(999383469l).count(15));
        assertTrue("size", 0 < statuses.size());
        statuses = unauthenticated.getUserTimeline(id1.name, new Paging(999383469l));
        assertTrue("size", 0 < statuses.size());

        statuses = twitterAPI1.getUserTimeline(new Paging(1).count(30));
        List<Status> statuses2 = twitterAPI1.getUserTimeline(new Paging(2).count(15));
        assertEquals(statuses.get(statuses.size() - 1), statuses2.get(statuses2.size() - 1));
    }

    public void testShowStatus() throws Exception {
        Status status = twitterAPI2.showStatus(1000l);
        assertEquals(52, status.getUser().getId());
        Status status2 = unauthenticated.showStatus(1000l);
        assertEquals(52, status2.getUser().getId());
        assertNotNull(status.getRateLimitStatus());

        status2 = unauthenticated.showStatus(999383469l);
        assertEquals("01010100 01110010 01101001 01110101 01101101 01110000 01101000       <3", status2.getText());
        status2 = unauthenticated.showStatus(7185737372l);
        assertEquals("\\u5e30%u5e30 <%}& foobar",status2.getText());


    }

    public void testStatusMethods() throws Exception {
        String date = new java.util.Date().toString() + "test";
        Status status = twitterAPI1.updateStatus(date);

        assertEquals(date, status.getText());
        Status status2 = twitterAPI2.updateStatus("@" + id1.name + " " + date, status.getId());
        assertEquals("@" + id1.name + " " + date, status2.getText());
        assertEquals(status.getId(), status2.getInReplyToStatusId());
        assertEquals(twitterAPI1.verifyCredentials().getId(), status2.getInReplyToUserId());
        twitterAPI1.destroyStatus(status.getId());
    }

    public void testRetweetMethods() throws Exception {
        List<Status> statuses = twitterAPI1.getRetweetedByMe();
        assertIsRetweet(statuses);
        statuses = twitterAPI1.getRetweetedByMe(new Paging(1));
        assertIsRetweet(statuses);
        statuses = twitterAPI1.getRetweetedToMe();
        assertIsRetweet(statuses);
        statuses = twitterAPI1.getRetweetedToMe(new Paging(1));
        assertIsRetweet(statuses);
        statuses = twitterAPI1.getRetweetsOfMe();
        assertIsRetweet(statuses);
        statuses = twitterAPI1.getRetweetsOfMe(new Paging(1));
        assertIsRetweet(statuses);
        statuses = twitterAPI1.getRetweets(1000);
        assertIsRetweet(statuses);
    }

    private void assertIsRetweet(List<Status> statuses) {
        for(Status status : statuses){
            assertTrue(status.isRetweet());
        }
    }

    public void testGeoLocation() throws Exception {
        Status withgeo = twitterAPI1.updateStatus(new java.util.Date().toString() + ": updating geo location", new GeoLocation(12.3456, -34.5678));
        assertTrue(withgeo.getUser().isGeoEnabled());
        assertEquals(12.3456, withgeo.getGeoLocation().getLatitude());
        assertEquals(-34.5678, withgeo.getGeoLocation().getLongitude());
        assertTrue(twitterAPI1.verifyCredentials().isGeoEnabled());
        assertFalse(twitterAPI2.verifyCredentials().isGeoEnabled());

        withgeo = twitterAPI1.showStatus(4512367904l);
        assertEquals(37.780300, withgeo.getGeoLocation().getLatitude());
        assertEquals(-122.396900, withgeo.getGeoLocation().getLongitude());
        assertTrue(withgeo.getUser().isGeoEnabled());

    }

    public void testGetFriendsStatuses() throws Exception {
        PagableResponseList<User> users = twitterAPI1.getFriendsStatuses();
        assertNotNull("friendsStatuses", users);

        users = twitterAPI1.getFriendsStatuses(numberId);
        assertNotNull("friendsStatuses", users);
        assertEquals(id1.name, users.get(0).getScreenName());

        users = twitterAPI1.getFriendsStatuses(numberIdId);
        assertNotNull("friendsStatuses", users);
        assertEquals(id1.name, users.get(0).getScreenName());

        users = unauthenticated.getFriendsStatuses("yusukey");
        assertNotNull("friendsStatuses", users);
    }

    public void testSocialGraphMethods() throws Exception {
        IDs ids;
        ids = twitterAPI1.getFriendsIDs();
        int yusukey = 4933401;
        assertIDExsits("twit4j is following yusukey", ids, yusukey);
        int JBossNewsJP = 28074579;
        int RedHatNewsJP = 28074306;
        ids = twitterAPI1.getFriendsIDs(JBossNewsJP);
        assertIDExsits("JBossNewsJP is following RedHatNewsJP", ids, RedHatNewsJP);
        ids = twitterAPI1.getFriendsIDs("RedHatNewsJP");
        assertIDExsits("RedHatNewsJP is following JBossNewsJP", ids, 28074579);
        IDs obamaFollowers;
        obamaFollowers = twitterAPI1.getFollowersIDs("barackobama");
        assertTrue(obamaFollowers.hasNext());
        assertFalse(obamaFollowers.hasPrevious());
        obamaFollowers = twitterAPI1.getFollowersIDs("barackobama", obamaFollowers.getNextCursor());
        assertTrue(obamaFollowers.hasNext());
        assertTrue(obamaFollowers.hasPrevious());

        obamaFollowers = twitterAPI1.getFollowersIDs(813286);
        assertTrue(obamaFollowers.hasNext());
        assertFalse(obamaFollowers.hasPrevious());
        obamaFollowers = twitterAPI1.getFollowersIDs(813286, obamaFollowers.getNextCursor());
        assertTrue(obamaFollowers.hasNext());
        assertTrue(obamaFollowers.hasPrevious());

        IDs obamaFriends;
        obamaFriends = twitterAPI1.getFriendsIDs("barackobama");
        assertTrue(obamaFriends.hasNext());
        assertFalse(obamaFriends.hasPrevious());
        obamaFriends = twitterAPI1.getFriendsIDs("barackobama", obamaFriends.getNextCursor());
        assertTrue(obamaFriends.hasNext());
        assertTrue(obamaFriends.hasPrevious());

        obamaFriends = twitterAPI1.getFriendsIDs(813286);
        assertTrue(obamaFriends.hasNext());
        assertFalse(obamaFriends.hasPrevious());
        obamaFriends = twitterAPI1.getFriendsIDs(813286, obamaFriends.getNextCursor());
        assertTrue(obamaFriends.hasNext());
        assertTrue(obamaFriends.hasPrevious());

        try {
            twitterAPI2.createFriendship(id1.name);
        } catch (TwitterException ignore) {
        }
        ids = twitterAPI1.getFollowersIDs();
        assertIDExsits("twit4j2 is following twit4j", ids, 6377362);
        ids = twitterAPI1.getFollowersIDs(28074579);
        assertIDExsits("RedHatNewsJP is following JBossNewsJP", ids, 28074306);
        ids = twitterAPI1.getFollowersIDs("JBossNewsJP");
        assertIDExsits("RedHatNewsJP is following JBossNewsJP", ids, 28074306);
    }


    public void testRelationship() throws Exception {
        //  TESTING PRECONDITIONS:
        //  1) id1 is followed by "followsOneWay", but not following "followsOneWay"
        Relationship rel1 = twitterAPI1.showFriendship(id1.name, followsOneWay);

        // test second precondition
        assertNotNull(rel1);
        assertTrue(rel1.isSourceFollowedByTarget());
        assertFalse(rel1.isSourceFollowingTarget());
        assertTrue(rel1.isTargetFollowingSource());
        assertFalse(rel1.isTargetFollowedBySource());

        //  2) best_friend1 is following and followed by best_friend2
        Relationship rel2 = twitterAPI1.showFriendship(bestFriend1.name, bestFriend2.name);

        // test second precondition
        assertNotNull(rel2);
        assertTrue(rel2.isSourceFollowedByTarget());
        assertTrue(rel2.isSourceFollowingTarget());
        assertTrue(rel2.isTargetFollowingSource());
        assertTrue(rel2.isTargetFollowedBySource());

        // test equality
        Relationship rel3 = twitterAPI1.showFriendship(id1.name, followsOneWay);
        assertEquals(rel1, rel3);
        assertFalse(rel1.equals(rel2));
    }

    private void assertIDExsits(String assertion, IDs ids, int idToFind) {
        boolean found = false;
        for (int id : ids.getIDs()) {
            if (id == idToFind) {
                found = true;
                break;
            }
        }
        assertTrue(assertion, found);
    }

    public void testAccountMethods() throws Exception {
        User original = twitterAPI1.verifyCredentials();
        if (original.getScreenName().endsWith("new") ||
                original.getName().endsWith("new")) {
            original = twitterAPI1.updateProfile(
                    "twit4j", null, "http://yusuke.homeip.net/twitter4j/"
                    , "location:", "Hi there, I do test a lot!new");

        }
        String newName, newURL, newLocation, newDescription;
        String neu = "new";
        newName = original.getName() + neu;
        newURL = original.getURL() + neu;
        newLocation = original.getLocation() + neu;
        newDescription = original.getDescription() + neu;

        User altered = twitterAPI1.updateProfile(
                newName, null, newURL, newLocation, newDescription);
        twitterAPI1.updateProfile(original.getName()
                , null, original.getURL().toString(), original.getLocation(), original.getDescription());
        assertEquals(newName, altered.getName());
        assertEquals(newURL, altered.getURL().toString());
        assertEquals(newLocation, altered.getLocation());
        assertEquals(newDescription, altered.getDescription());

        try {
            new TwitterFactory().getBasicAuthorizedInstance("doesnotexist--", "foobar").verifyCredentials();
            fail("should throw TwitterException");
        } catch (TwitterException te) {
        }

        twitterAPI1.updateDeliveryDevice(Device.SMS);
        assertTrue(twitterAPIBestFriend1.existsFriendship(bestFriend1.name, bestFriend2.name));
        assertFalse(twitterAPI1.existsFriendship(id1.name, "al3x"));

        User eu;
        eu = twitterAPI1.updateProfileColors("f00", "f0f", "0ff", "0f0", "f0f");
        assertEquals("f00", eu.getProfileBackgroundColor());
        assertEquals("f0f", eu.getProfileTextColor());
        assertEquals("0ff", eu.getProfileLinkColor());
        assertEquals("0f0", eu.getProfileSidebarFillColor());
        assertEquals("f0f", eu.getProfileSidebarBorderColor());
        eu = twitterAPI1.updateProfileColors("f0f", "f00", "f0f", "0ff", "0f0");
        assertEquals("f0f", eu.getProfileBackgroundColor());
        assertEquals("f00", eu.getProfileTextColor());
        assertEquals("f0f", eu.getProfileLinkColor());
        assertEquals("0ff", eu.getProfileSidebarFillColor());
        assertEquals("0f0", eu.getProfileSidebarBorderColor());
        eu = twitterAPI1.updateProfileColors("87bc44", "9ae4e8", "000000", "0000ff", "e0ff92");
        assertEquals("87bc44", eu.getProfileBackgroundColor());
        assertEquals("9ae4e8", eu.getProfileTextColor());
        assertEquals("000000", eu.getProfileLinkColor());
        assertEquals("0000ff", eu.getProfileSidebarFillColor());
        assertEquals("e0ff92", eu.getProfileSidebarBorderColor());
        eu = twitterAPI1.updateProfileColors("f0f", null, "f0f", null, "0f0");
        assertEquals("f0f", eu.getProfileBackgroundColor());
        assertEquals("9ae4e8", eu.getProfileTextColor());
        assertEquals("f0f", eu.getProfileLinkColor());
        assertEquals("0000ff", eu.getProfileSidebarFillColor());
        assertEquals("0f0", eu.getProfileSidebarBorderColor());
        eu = twitterAPI1.updateProfileColors(null, "f00", null, "0ff", null);
        assertEquals("f0f", eu.getProfileBackgroundColor());
        assertEquals("f00", eu.getProfileTextColor());
        assertEquals("f0f", eu.getProfileLinkColor());
        assertEquals("0ff", eu.getProfileSidebarFillColor());
        assertEquals("0f0", eu.getProfileSidebarBorderColor());
        eu = twitterAPI1.updateProfileColors("9ae4e8", "000000", "0000ff", "e0ff92", "87bc44");
        assertEquals("9ae4e8", eu.getProfileBackgroundColor());
        assertEquals("000000", eu.getProfileTextColor());
        assertEquals("0000ff", eu.getProfileLinkColor());
        assertEquals("e0ff92", eu.getProfileSidebarFillColor());
        assertEquals("87bc44", eu.getProfileSidebarBorderColor());
    }

    public void testAccountProfileImageUpdates() throws Exception {
        twitterAPI1.updateProfileImage(getRandomlyChosenFile());
        // tile randomly
        twitterAPI1.updateProfileBackgroundImage(getRandomlyChosenFile(),
                (5 < System.currentTimeMillis() % 5));
    }
    static final String[] files = {"src/test/resources/t4j-reverse.jpeg",
            "src/test/resources/t4j-reverse.png",
            "src/test/resources/t4j-reverse.gif",
            "src/test/resources/t4j.jpeg",
            "src/test/resources/t4j.png",
            "src/test/resources/t4j.gif",
    };
    public static File getRandomlyChosenFile(){
        return new File(files[(int) (System.currentTimeMillis() % 6)]);
    }

    public void testFavoriteMethods() throws Exception {
        Status status = twitterAPI1.getHomeTimeline(new Paging().count(1)).get(0);
        twitterAPI2.createFavorite(status.getId());
        assertTrue(twitterAPI2.getFavorites().size() > 0);
        try {
            twitterAPI2.destroyFavorite(status.getId());
        } catch (TwitterException te) {
            // sometimes destorying favorite fails with 404
            assertEquals(404, te.getStatusCode());
        }
    }

    public void testFollowers() throws Exception {
        PagableResponseList<User> actualReturn = twitterAPI1.getFollowersStatuses();
        assertTrue(actualReturn.size() > 0);
        actualReturn = twitterAPI1.getFollowersStatuses();
        assertTrue(actualReturn.size() > 0);
        assertFalse(actualReturn.hasNext());
        assertFalse(actualReturn.hasPrevious());
        actualReturn = twitterAPI1.getFollowersStatuses(actualReturn.getNextCursor());
        assertEquals(0, actualReturn.size());

        actualReturn = twitterAPI2.getFollowersStatuses();
        assertTrue(actualReturn.size() > 0);
        actualReturn = twitterAPI2.getFollowersStatuses("yusukey");
        assertTrue(actualReturn.size() > 60);
        actualReturn = twitterAPI2.getFollowersStatuses("yusukey", actualReturn.getNextCursor());
        assertTrue(actualReturn.size() > 10);
    }

    public void testDirectMessages() throws Exception {
        String expectedReturn = new Date() + ":directmessage test";
        DirectMessage actualReturn = twitterAPI1.sendDirectMessage("twit4jnoupdate", expectedReturn);
        assertEquals(expectedReturn, actualReturn.getText());
        assertEquals(id1.name, actualReturn.getSender().getScreenName());
        assertEquals("twit4jnoupdate", actualReturn.getRecipient().getScreenName());
        List<DirectMessage> actualReturnList = twitterAPI1.getDirectMessages();
        assertTrue(1 <= actualReturnList.size());
    }

    public void testCreateDestroyFriend() throws Exception {
        User user;
        try {
            user = twitterAPI2.destroyFriendship(id1.name);
        } catch (TwitterException te) {
            //ensure destory id1 before the actual test
            //ensure destory id1 before the actual test
        }

        try {
            user = twitterAPI2.destroyFriendship(id1.name);
        } catch (TwitterException te) {
            assertEquals(403, te.getStatusCode());
        }
        user = twitterAPI2.createFriendship(id1.name, true);
        assertEquals(id1.name, user.getScreenName());
        // the Twitter API is not returning appropriate notifications value
        // http://code.google.com/p/twitter-api/issues/detail?id=474
//        User detail = twitterAPI2.showUser(id1);
//        assertTrue(detail.isNotificationEnabled());
        try {
            user = twitterAPI2.createFriendship(id2.name);
            fail("shouldn't be able to befrinend yourself");
        } catch (TwitterException te) {
            assertEquals(403, te.getStatusCode());
        }
        try {
            user = twitterAPI2.createFriendship("doesnotexist--");
            fail("non-existing user");
        } catch (TwitterException te) {
            //now befriending with non-existing user returns 404
            //http://groups.google.com/group/twitter-development-talk/browse_thread/thread/bd2a912b181bc39f
            assertEquals(404, te.getStatusCode());
        }

    }

    public void testGetMentions() throws Exception {
        twitterAPI2.updateStatus("@" + id1.name + " reply to id1");
        List<Status> statuses = twitterAPI1.getMentions();
        assertTrue(statuses.size() > 0);

        statuses = twitterAPI1.getMentions(new Paging(1));
        assertTrue(statuses.size() > 0);
        statuses = twitterAPI1.getMentions(new Paging(1));
        assertTrue(statuses.size() > 0);
        statuses = twitterAPI1.getMentions(new Paging(1, 1l));
        assertTrue(statuses.size() > 0);
        statuses = twitterAPI1.getMentions(new Paging(1l));
        assertTrue(statuses.size() > 0);
    }

    public void testNotification() throws Exception {
        try {
            twitterAPI1.disableNotification("twit4jprotected");
        } catch (TwitterException te) {
        }
        twitterAPI1.enableNotification("twit4jprotected");
        twitterAPI1.disableNotification("twit4jprotected");
    }

    public void testBlock() throws Exception {
        twitterAPI2.createBlock(id1.name);
        twitterAPI2.destroyBlock(id1.name);
        assertFalse(twitterAPI1.existsBlock("twit4j2"));
        assertTrue(twitterAPI1.existsBlock("twit4jblock"));
        List<User> users = twitterAPI1.getBlockingUsers();
        assertEquals(1, users.size());
        assertEquals(39771963, users.get(0).getId());
        users = twitterAPI1.getBlockingUsers(1);
        assertEquals(1, users.size());
        assertEquals(39771963, users.get(0).getId());

        IDs ids = twitterAPI1.getBlockingUsersIDs();
        assertEquals(1, ids.getIDs().length);
        assertEquals(39771963, ids.getIDs()[0]);
    }


    RateLimitStatus rateLimitStatus = null;
    boolean accountLimitStatusAcquired;
    boolean ipLimitStatusAcquired;
    //need to think of a way to test this, perhaps mocking out Twitter is the way to go
    public void testRateLimitStatus() throws Exception {
        RateLimitStatus status = twitterAPI1.getRateLimitStatus();
        assertTrue(10 < status.getHourlyLimit());
        assertTrue(10 < status.getRemainingHits());

        twitterAPI1.addRateLimitStatusListener(new RateLimitStatusListener() {

            public void onRateLimitStatus(RateLimitStatusEvent event) {
                accountLimitStatusAcquired = event.isAccountRateLimitStatus();
                ipLimitStatusAcquired = event.isIPRateLimitStatus();
                rateLimitStatus = event.getRateLimitStatus();
            }

            public void onRateLimitReached(RateLimitStatusEvent event) {

            }

        });

        unauthenticated.addRateLimitStatusListener(new RateLimitStatusListener() {
            public void onRateLimitStatus(RateLimitStatusEvent event) {
                accountLimitStatusAcquired = event.isAccountRateLimitStatus();
                ipLimitStatusAcquired = event.isIPRateLimitStatus();
                rateLimitStatus = event.getRateLimitStatus();
            }
            public void onRateLimitReached(RateLimitStatusEvent event){
            }
        });

        twitterAPI1.getMentions();
        assertTrue(accountLimitStatusAcquired);
        assertFalse(ipLimitStatusAcquired);
        RateLimitStatus previous = rateLimitStatus;
        twitterAPI1.getMentions();
        assertTrue(accountLimitStatusAcquired);
        assertFalse(ipLimitStatusAcquired);
        assertTrue(previous.getRemainingHits() > rateLimitStatus.getRemainingHits());
        assertEquals(previous.getHourlyLimit(), rateLimitStatus.getHourlyLimit());

        unauthenticated.getPublicTimeline();
        assertFalse(accountLimitStatusAcquired);
        assertTrue(ipLimitStatusAcquired);
        previous = rateLimitStatus;
        unauthenticated.getPublicTimeline();
        assertFalse(accountLimitStatusAcquired);
        assertTrue(ipLimitStatusAcquired);
        assertTrue(previous.getRemainingHits() > rateLimitStatus.getRemainingHits());
        assertEquals(previous.getHourlyLimit(), rateLimitStatus.getHourlyLimit());
    }


    public void testSavedSearches() throws Exception {
        List<SavedSearch> list = twitterAPI1.getSavedSearches();
        for (SavedSearch savedSearch : list) {
            twitterAPI1.destroySavedSearch(savedSearch.getId());
        }
        SavedSearch ss1 = twitterAPI1.createSavedSearch("my search");
        assertEquals("my search", ss1.getQuery());
        assertEquals(-1, ss1.getPosition());
        list = twitterAPI1.getSavedSearches();
        // http://code.google.com/p/twitter-api/issues/detail?id=1032
        // the saved search may not be immediately available
        assertTrue(0 <= list.size());
        try {
            SavedSearch ss2 = twitterAPI1.destroySavedSearch(ss1.getId());
            assertEquals(ss1, ss2);
        } catch (TwitterException te) {
            // sometimes it returns 404 when its out of sync.
            assertEquals(404, te.getStatusCode());
        }
    }

    public void testTest() throws Exception {
        assertTrue(twitterAPI2.test());
    }
}
