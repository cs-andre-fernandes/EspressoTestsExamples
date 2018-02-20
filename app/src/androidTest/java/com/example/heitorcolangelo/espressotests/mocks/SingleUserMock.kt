package fixtures

/**
 * Created by andre on 16/02/18.
 */
class SingleUserMock {

    companion object {
        fun singleUserMock() = "{\n" +
                "\n" +
                "      \"gender\": \"female\",\n" +
                "      \"name\": {\n" +
                "        \"title\": \"mrs\",\n" +
                "        \"first\": \"ivanna\",\n" +
                "        \"last\": \"molina\"\n" +
                "      },\n" +
                "      \"location\": {\n" +
                "        \"street\": \"8969 willem van noortplein\",\n" +
                "        \"city\": \"twenterand\",\n" +
                "        \"state\": \"overijssel\",\n" +
                "        \"postcode\": 88284\n" +
                "      },\n" +
                "      \"email\": \"ivanna.molina@example.com\",\n" +
                "      \"login\": {\n" +
                "        \"username\": \"goldenleopard173\",\n" +
                "        \"password\": \"diao\",\n" +
                "        \"salt\": \"phFO4BbQ\",\n" +
                "        \"md5\": \"0cb98ee7d6e1bebbe86a69bd482d02af\",\n" +
                "        \"sha1\": \"f6c61940681da437a19ad94908e6c41f684c5ae0\",\n" +
                "        \"sha256\": \"c2bc9af43c6eab7acfd6863759b224c4c9f43b3e188a5a1b8ce9de54fca7f954\"\n" +
                "      },\n" +
                "      \"dob\": \"1986-05-18 21:49:48\",\n" +
                "      \"registered\": \"2007-02-13 03:34:31\",\n" +
                "      \"phone\": \"(021)-064-8780\",\n" +
                "      \"cell\": \"(397)-714-2191\",\n" +
                "      \"id\": {\n" +
                "        \"name\": \"BSN\",\n" +
                "        \"value\": \"46007974\"\n" +
                "      },\n" +
                "      \"picture\": {\n" +
                "        \"large\": \"https://randomuser.me/api/portraits/women/31.jpg\",\n" +
                "        \"medium\": \"https://randomuser.me/api/portraits/med/women/31.jpg\",\n" +
                "        \"thumbnail\": \"https://randomuser.me/api/portraits/thumb/women/31.jpg\"\n" +
                "      },\n" +
                "      \"nat\": \"NL\",\n" +
                "  \"info\": {\n" +
                "    \"seed\": \"088ee1c7d9e4672f\",\n" +
                "    \"results\": 1,\n" +
                "    \"page\": 1,\n" +
                "    \"version\": \"1.1\"\n" +
                "  }\n" +
                "}"

        fun userWithoutInfos() = "{\n" +
                "\n" +
                "      \"gender\": \"female\",\n" +
                "      \"name\": {\n" +
                "        \"title\": \"mrs\",\n" +
                "        \"first\": \"ivanna\",\n" +
                "        \"last\": \"molina\"\n" +
                "      },\n" +
                "      \"location\": {\n" +
                "        \"street\": \"8969 willem van noortplein\",\n" +
                "        \"city\": \"twenterand\",\n" +
                "        \"state\": \"overijssel\",\n" +
                "        \"postcode\": 88284\n" +
                "      },\n" +
                "      \"email\": \"\",\n" +
                "      \"login\": {\n" +
                "        \"username\": \"goldenleopard173\",\n" +
                "        \"password\": \"diao\",\n" +
                "        \"salt\": \"phFO4BbQ\",\n" +
                "        \"md5\": \"0cb98ee7d6e1bebbe86a69bd482d02af\",\n" +
                "        \"sha1\": \"f6c61940681da437a19ad94908e6c41f684c5ae0\",\n" +
                "        \"sha256\": \"c2bc9af43c6eab7acfd6863759b224c4c9f43b3e188a5a1b8ce9de54fca7f954\"\n" +
                "      },\n" +
                "      \"dob\": \"1986-05-18 21:49:48\",\n" +
                "      \"registered\": \"2007-02-13 03:34:31\",\n" +
                "      \"phone\": \"(021)-064-8780\",\n" +
                "      \"cell\": \"(397)-714-2191\",\n" +
                "      \"id\": {\n" +
                "        \"name\": \"BSN\",\n" +
                "        \"value\": \"46007974\"\n" +
                "      },\n" +
                "      \"picture\": {\n" +
                "        \"large\": \"https://randomuser.me/api/portraits/women/31.jpg\",\n" +
                "        \"medium\": \"https://randomuser.me/api/portraits/med/women/31.jpg\",\n" +
                "        \"thumbnail\": \"https://randomuser.me/api/portraits/thumb/women/31.jpg\"\n" +
                "      },\n" +
                "      \"nat\": \"NL\",\n" +
                "  \"info\": {\n" +
                "    \"seed\": \"088ee1c7d9e4672f\",\n" +
                "    \"results\": 1,\n" +
                "    \"page\": 1,\n" +
                "    \"version\": \"1.1\"\n" +
                "  }\n" +
                "}"
    }

}