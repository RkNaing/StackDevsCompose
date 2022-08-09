package com.rahulkumar.stackdevs.data.resource

sealed class ErrorEntity : Exception() {

    /**
     * An [ErrorEntity] for representing error related to REST API calls.
     */
    sealed class Rest : ErrorEntity() {
        /**
         * Represents that **NULL** is received as server's response body.
         */
        object NullResponse : Rest() {

            override fun toString(): String {
                return "NULL Response Error Entity"
            }
        }

        /**
         * Represents failure of establish connection to server due to
         *
         * - No internet access (device is offline)
         * - Poor internet connection
         * - Laggy sever
         *
         * @property isTimeout  - **true** if took too long to get server response due to
         * poor internet connection or laggy server
         * - **false** if no internet access.
         */
        class Network(val isTimeout: Boolean) : Rest() {

            override fun toString(): String {
                return "Network Error Entity [isTimeout : $isTimeout]"
            }
        }

        /**
         * Represents an unsuccessful HTTP status code is received
         * in response i.e, not within 200..299.
         *
         * @property [HTTP Status Code](https://tinyurl.com/ybsa8qmz)
         */
        class Http(val code: Int) : Rest() {

            /**
             * **true** if [code] is between 500 and 599
             */
            val isServerError: Boolean = code in 500 until 600

            override fun toString(): String {
                return "HTTP Error Entity [HTTP Code : $code]"
            }
        }

        /**
         * Represents other unhandled errors.
         */
        object Unknown : Rest() {
            override fun toString(): String {
                return "Unknown REST Error Entity"
            }
        }
    }

}