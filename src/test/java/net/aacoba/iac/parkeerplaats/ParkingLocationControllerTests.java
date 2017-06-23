/*
 * Copyright 2016 the original author or authors.
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
package net.aacoba.iac.parkeerplaats;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLocationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testParkingSpotOnInit() throws Exception {
        assertParkingLocationCapacity(1, 100);
    }


    @Test
    public void testIsParkingSpotTaken() throws Exception {
        int parking_spot_id = claimParkingSpot(1);

        boolean is_spot_claimed = isSpotUsed(1, parking_spot_id);
        Assert.assertTrue("Claimed parking spot ID was NOT found in list of used parkingspots", is_spot_claimed);
    }

    @Test
    public void testIsSpotReleased() throws Exception {
        int parking_spot_id = claimParkingSpot(1);

        boolean is_spot_claimed = isSpotUsed(1, parking_spot_id);
        Assert.assertTrue("Claimed parking spot ID was NOT found in list of used parkingspots", is_spot_claimed);
        releaseParkingSpot(1, parking_spot_id);
        boolean is_spot_still_claimed = isSpotUsed(1, parking_spot_id);
        Assert.assertFalse("Claimed parking spot ID was NOT released!", is_spot_still_claimed);
    }

    private void assertParkingLocationCapacity(int parkinglocationid, int capacity) throws Exception {
        this.mockMvc.perform(get("/parkinglocation/" + parkinglocationid)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.totalCapacity").value(capacity));
    }


    private int claimParkingSpot(int parkingLocation) throws Exception {
        MvcResult result = this.mockMvc.perform(post("/parkinglocation/" + parkingLocation + "/claim")).andDo(print())
                .andExpect(status().isOk()).andReturn();

        String resultString = result.getResponse().getContentAsString();
        JSONObject jsonObject = new JSONObject(resultString);
        return jsonObject.getInt("id");
    }

    private void releaseParkingSpot(int parkingLocation, int parkingSpotId) throws Exception {
        MvcResult result = this.mockMvc.perform(post("/parkinglocation/" + parkingLocation + "/release/" + parkingSpotId)).andDo(print())
                .andExpect(status().isOk()).andReturn();
    }

    private boolean isSpotUsed(int parkingLocation, int parking_spot_id) throws Exception {
        MvcResult result_get_status = this.mockMvc.perform(get("/parkinglocation/" + parkingLocation))
                .andExpect(status().isOk()).andReturn();

        String statusReturnString = result_get_status.getResponse().getContentAsString();
        JSONObject statusJson = new JSONObject(statusReturnString);
        JSONArray used_spots = statusJson.getJSONArray("usedSpots");
        boolean found_in_used_spots = false;
        for (int i = 0; i < used_spots.length(); i++) {
            int list_spot_id = used_spots.getInt(i);
            if (list_spot_id == parking_spot_id) {
                found_in_used_spots = true;
            }
        }
        return found_in_used_spots;
    }

}
