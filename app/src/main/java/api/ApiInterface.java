package api;

import java.util.List;

import model.AccountModel;
import model.AvailbiltyModel;
import model.BookTulModel;
import model.CallResendModel;
import model.CancellationPolicyModel;
import model.CardLocalModel;
import model.CardModel;
import model.CategoriesModel;
import model.CheckChatModel;
import model.CreateStripeAccountModel;
import model.CreateTulModel;
import model.DashboardDatesModel;
import model.DashboardMyTulModel;
import model.DeleteCardModel;
import model.DemoModel;
import model.LentTulModel;
import model.MyBookingModel;
import model.NearByTulListingModel;
import model.NearByTulModel;
import model.NotificationModel;
import model.PendingTaskModel;
import model.ProfileModel;
import model.ReviewRatingModel;
import model.SalesBookTulModel;
import model.SalesBookingModel;
import model.SalesHistoryTulDetailModel;
import model.SalesListingTulModel;
import model.SalesPriceFee;
import model.SalesSellerModel;
import model.SalesTulDetailModel;
import model.SearchResultModel;
import model.SellerDetailsModel;
import model.SignupModel;
import model.StatisticsModel;
import model.TransactionModel;
import model.VersionModel;
import model.ViewTulModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    //API Method to get Questions From API

    @FormUrlEncoded
    @POST("api/v2/users/signup")
        // /users/signup
    Call<SignupModel> userSignup(@Field("email") String email,
                                 @Field("password") String password,
                                 @Field("platform_status") String platform_status,
                                 @Field("user_type") String user_type,
                                 @Field("device_token") String device_token,
                                 @Field("facebook_id") String facebook_id,
                                 @Field("facebook_url") String facebook_url,
                                 @Field("first_name") String first_name,
                                 @Field("last_name") String last_name,
                                 @Field("login_type") String login_type);


    @FormUrlEncoded
    @POST("/users/guest_signin")
    Call<SignupModel> userSkip(@Field("imei") String imei, @Field("login_type") String login_type);


    @FormUrlEncoded
    @POST("/users/signin")
    Call<SignupModel> userSignin(@Field("email") String email,
                                 @Field("password") String password,
                                 @Field("platform_status") String platform_status,
                                 @Field("device_token") String device_token,
                                 @Field("login_type") String login_type);

    @Multipart
    @POST("/users/create_profile")
    Call<SignupModel> createProfile(@Part("first_name") RequestBody first_name,
                                    @Part("last_name") RequestBody last_name,
                                    @Part("country_code") RequestBody country_code,
                                    @Part("phone_number") RequestBody phone_number,
                                    @Part("access_token") RequestBody access_token,
                                    @Part MultipartBody.Part image);


    @Multipart
    @POST("/api/sale/v1/users")
    Call<SignupModel> createSellerProfile(@Part("user_first_name") RequestBody user_first_name,
                                          @Part("user_last_name") RequestBody user_last_name,
                                          @Part("pin_code") RequestBody pin_code,
                                          @Part("phone_number") RequestBody phone_number,
                                          @Part("country_code") RequestBody country_code,
                                          @Part("currency") RequestBody currency,
                                          @Part("account_number") RequestBody account_number,
                                          @Part("sort_code") RequestBody sort_code,
                                          @Part("first_name") RequestBody first_name,
                                          @Part("last_name") RequestBody last_name,
                                          @Part("address") RequestBody address,
                                          @Part("city") RequestBody city,
                                          @Part("postal_code") RequestBody postal_code,
                                          @Part("state") RequestBody state,
                                          @Part("dob") RequestBody dob,
                                          @Part("location") RequestBody location,
                                          @Part("latitude") RequestBody latitude,
                                          @Part("longitude") RequestBody longitude,
                                          @Part("access_token") RequestBody access_token,
                                          @Part("is_company") RequestBody is_company,
                                          @Part("swift") RequestBody swift,
                                          @Part("is_account_added") RequestBody is_account_added,
                                          @Part("is_number_updated") RequestBody Is_number_updated,
                                          @Part("is_seller") RequestBody is_seller,
                                          @Part("vat") RequestBody vat,
                                          @Part("account_type") RequestBody account_type,
                                          @Part("email_changed") RequestBody email_changed,
                                          @Part("email") RequestBody email,
                                          @Part MultipartBody.Part profile_pic,
                                          @Part MultipartBody.Part document);

    @FormUrlEncoded
    @POST("/users/verify")
    Call<SignupModel> userVerify(@Field("access_token") String access_token,
                                 @Field("otp") String otp);

    @FormUrlEncoded
    @POST("/api/v2/users/verify")
    Call<SignupModel> userSellerVerify(@Field("access_token") String access_token,
                                       @Field("otp") String otp,
                                       @Field("pin_code") String pin_code,
                                       @Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("/users/call")
    Call<SignupModel> callUser(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("/api/v2/users/call")
    Call<SignupModel> callSellerUser(@Field("access_token") String access_token,
                                     @Field("pin_code") String pin_code,
                                     @Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("/users/resend")
    Call<SignupModel> callResend(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("/api/v2/users/resend")
    Call<SignupModel> callSellerResend(@Field("access_token") String access_token,
                                       @Field("pin_code") String pin_code,
                                       @Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("/users/forget_password")
    Call<CallResendModel> forgetPassword(@Field("email") String email);

    @POST("/tools/categories")
    Call<CategoriesModel> getCategories();


    @Multipart
    @POST("/users/update_profile")
    Call<SignupModel> updateProfile(@Part("access_token") RequestBody access_token,
                                    @Part("first_name") RequestBody first_name,
                                    @Part("last_name") RequestBody last_name,
                                    @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("/api/sale/v1/users/view_profile")
    Call<ProfileModel> viewSellerProfile(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("users/view_profile")
    Call<ProfileModel> viewProfile(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("users/other_user_profile")
    Call<NearByTulListingModel> otherUserProfile(@Field("access_token") String access_token,
                                                 @Field("user_id") int user_id);

    @GET("/api/sale/v1/seller_profile")
    Call<SellerDetailsModel> sellerProfile(@Query("access_token") String access_token,
                                           @Query("user_id") int user_id);

    @Multipart
    @POST("payment/stripe_account")
    Call<CreateStripeAccountModel> stripeBank(@Part("access_token") RequestBody access_token,
                                              @Part("country_code") RequestBody country_code,
                                              @Part("currency") RequestBody currency,
                                              @Part("account_number") RequestBody account_number,
                                              @Part("first_name") RequestBody first_name,
                                              @Part("last_name") RequestBody last_name,
                                              @Part("address") RequestBody address,
                                              @Part("city") RequestBody city,
                                              @Part("state") RequestBody state,
                                              @Part("postal_code") RequestBody postal_code,
                                              @Part("dob") RequestBody dob,
                                              @Part("sort_code") RequestBody sort_code,
                                              @Part("swift") RequestBody swift,
                                              @Part("account_type") RequestBody account_type,
                                              @Part MultipartBody.Part document);

    @Multipart
    @POST("v2/tools/create_tool")
    Call<CreateTulModel> createTul(@Part("access_token") RequestBody access_token,
                                   @Part("title") RequestBody title,
                                   @Part("category_id") RequestBody category_id,
                                   @Part("description") RequestBody description,
                                   @Part("quantity") RequestBody quantity,
                                   @Part("price") RequestBody price,
                                   @Part("discount_days") RequestBody discount_days,
                                   @Part("discount") RequestBody discount,
                                   @Part("tool_currency") RequestBody tool_currency,
                                   @Part("additional_price") RequestBody additional_price,
                                   @Part("tool_address") RequestBody tool_address,
                                   @Part("latitude") RequestBody latitude,
                                   @Part("longitude") RequestBody longitude,
                                   @Part("rules") RequestBody rules,
                                   @Part("preferences") RequestBody preferences,
                                   @Part("stripe_account") RequestBody stripe_account,
                                   @Part("transaction_percentage") RequestBody transaction_percentage,
                                   @Part("primary_currency") RequestBody primary_currency,
                                   @Part List<MultipartBody.Part> files,
                                   @Part MultipartBody.Part document,
                                   @Part MultipartBody.Part v_thumbnail);


    @Multipart
    @POST("/api/sale/v1/tools/create_tool")
    Call<CreateTulModel> createSalesTul(@Part("access_token") RequestBody access_token,
                                        @Part("title") RequestBody title,
                                        @Part("description") RequestBody description,
                                        @Part("quantity") RequestBody quantity,
                                        @Part("price") RequestBody price,
                                        @Part("condition") RequestBody condition,
                                        @Part("delivery_type") RequestBody delivery_type,
                                        @Part("delivery_charges_local") RequestBody delivery_charges_local,
                                        @Part("delivery_charges_int") RequestBody delivery_charges_int,
                                        @Part("currency") RequestBody currency,
                                        @Part("primary_currency") RequestBody primary_currency,
                                        @Part List<MultipartBody.Part> files,
                                        @Part MultipartBody.Part document,
                                        @Part MultipartBody.Part v_thumbnail);

    @Multipart
    @POST("/api/sale/v1/tools/edit_sale_tool")
    Call<SalesTulDetailModel> editSalesTul(@Part("access_token") RequestBody access_token,
                                           @Part("id") RequestBody id,
                                           @Part("title") RequestBody title,
                                           @Part("description") RequestBody description,
                                           @Part("quantity") RequestBody quantity,
                                           @Part("price") RequestBody price,
                                           @Part("condition") RequestBody condition,
                                           @Part("delivery_type") RequestBody delivery_type,
                                           @Part("delivery_charges_local") RequestBody delivery_charges_local,
                                           @Part("delivery_charges_int") RequestBody delivery_charges_int,
                                           @Part("currency") RequestBody currency,
                                           @Part("primary_currency") RequestBody primary_currency,
                                           @Part("attachment_ids") RequestBody attachment_ids,
                                           @Part List<MultipartBody.Part> files,
                                           @Part MultipartBody.Part document,
                                           @Part MultipartBody.Part v_thumbnail);

    @Multipart
    @POST("tools/edit_tool")
    Call<ViewTulModel> editTul(@Part("access_token") RequestBody access_token,
                               @Part("title") RequestBody title,
                               @Part("category_id") RequestBody category_id,
                               @Part("description") RequestBody description,
                               @Part("quantity") RequestBody quantity,
                               @Part("price") RequestBody price,
                               @Part("discount_days") RequestBody discount_days,
                               @Part("discount") RequestBody discount,
                               @Part("tool_currency") RequestBody tool_currency,
                               @Part("additional_price") RequestBody additional_price,
                               @Part("tool_address") RequestBody tool_address,
                               @Part("latitude") RequestBody latitude,
                               @Part("longitude") RequestBody longitude,
                               @Part("rules") RequestBody rules,
                               @Part("preferences") RequestBody preferences,
                               @Part("tool_id") RequestBody tool_id,
                               @Part("attachment_ids") RequestBody attachment_ids,
                               @Part("transaction_percentage") RequestBody transaction_percentage,
                               @Part("primary_currency") RequestBody primary_currency,
                               @Part List<MultipartBody.Part> files,
                               @Part MultipartBody.Part document,
                               @Part MultipartBody.Part v_thumbnail);

    @Multipart
    @POST("add_image")
    Call<CreateTulModel> demoAPI(@Part List<MultipartBody.Part> files);

    @FormUrlEncoded
    @POST("tools/view_tool")
    Call<ViewTulModel> viewTul(@Field("access_token") String access_token,
                               @Field("tool_id") int tool_id);

    @GET("api/sale/v1/tool_detail")
    Call<SalesTulDetailModel> salesToolDetail(@Query("access_token") String access_token,
                                              @Query("id") int tool_id);

    @GET("/api/sale/v1/fee")
    Call<SalesPriceFee> salesPriceFee(@Query("access_token") String access_token);

    @FormUrlEncoded
    @POST("tools/delete_tool")
    Call<DemoModel> deleteTul(@Field("access_token") String access_token,
                              @Field("tool_id") int tool_id);

    @FormUrlEncoded
    @POST("/api/sale/v1/tools/delete_a_sale_tool")
    Call<DemoModel> delete_a_sale_tool(@Field("access_token") String access_token,
                                       @Field("id") int tool_id);


    @FormUrlEncoded
    @POST("tools/near_by_tools")
    Call<NearByTulModel> nearByTools(@Field("access_token") String access_token,
                                     @Field("latitude") String latitude,
                                     @Field("longitude") String longitude);


    @GET("api/sale/v1/sellers")
    Call<SalesSellerModel> nearByToolsSales(@Query("access_token") String access_token,
                                            @Query("latitude") String latitude,
                                            @Query("longitude") String longitude);

    @FormUrlEncoded
    @POST("tools/get_landing_tools")
    Call<NearByTulListingModel> getNearByToolsList(@Field("access_token") String access_token,
                                                   @Field("tool_ids") String tool_ids);

    @FormUrlEncoded
    @POST("api/sale/v1/sellers/sellers_by_id")
    Call<SalesListingTulModel> getNearByToolsListSales(@Field("access_token") String access_token,
                                                       @Field("ids") String tool_ids);

    @GET("api/sale/v1/searches")
    Call<SalesListingTulModel> salesSearch(@Query("access_token") String access_token,
                                           @Query("title") String title,
                                           @Query("max_price") String max_price,
                                           @Query("min_price") String min_price,
                                           @Query("latitude") String latitude,
                                           @Query("longitude") String longitude,
                                           @Query("condition") String condition,
                                           @Query("rating") String rating,
                                           @Query("distance") String distance);

    @GET("/api/sale/v1/seller_list_view")
    Call<SalesListingTulModel> sellerTullistview(@Query("access_token") String access_token, @Query("latitude") String latitude,
                                                 @Query("longitude") String longitude);

    @FormUrlEncoded
    @POST("tools/tools_by_category")
    Call<NearByTulListingModel> getToolsByCategory(@Field("access_token") String access_token,
                                                   @Field("category_id") String category_id,
                                                   @Field("offset") int offset);

    @FormUrlEncoded
    @POST("users/wishlist")
    Call<DemoModel> shortListTul(@Field("access_token") String access_token,
                                 @Field("tool_id") int tool_id);

    @FormUrlEncoded
    @POST("/api/sale/v1/wishlists")
    Call<DemoModel> shortListTulSales(@Field("access_token") String access_token,
                                      @Field("id") int tool_id);


    @FormUrlEncoded
    @POST("feedback/report")
    Call<DemoModel> ReportTul(@Field("access_token") String access_token,
                              @Field("tool_id") int tool_id,
                              @Field("report_type") int report_type,
                              @Field("lender_id") int lender_id,
                              @Field("borrower_id") int borrower_id,
                              @Field("report_from") int reportFrom);

    @FormUrlEncoded
    @POST("/api/sale/v1/reports")
    Call<DemoModel> ReportSele(@Field("access_token") String access_token,
                               @Field("id") int id);

    @FormUrlEncoded
    @POST("payment/view_cards")
    Call<CardModel> viewCard(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("payment/delete_card")
    Call<DeleteCardModel> deleteCard(@Field("access_token") String access_token, @Field("card_id") Integer card_id);

    @FormUrlEncoded
    @POST("payment/save_card")
    Call<CardLocalModel> saveCard(@Field("access_token") String access_token,
                                  @Field("card_number") String card_number,
                                  @Field("name_on_card") String name_on_card,
                                  @Field("expiry_month") Integer expiry_month,
                                  @Field("expiry_year") Integer expiry_year);

    @FormUrlEncoded
    @POST("payment/bank_accounts")
    Call<AccountModel> getAccounts(@Field("access_token") String access_token);


    @FormUrlEncoded
    @POST("payment/delete_bank_account")
    Call<DemoModel> deleteAccount(@Field("access_token") String access_token,
                                  @Field("account_id") int account_id);


    @FormUrlEncoded
    @POST("payment/make_account_primary")
    Call<DemoModel> makePrimaryAccount(@Field("access_token") String access_token,
                                       @Field("account_id") int account_id);

    @FormUrlEncoded
    @POST("payment/get_primary_account")
    Call<CreateStripeAccountModel> getPrimaryAccount(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("tools/get_more_tools")
    Call<NearByTulListingModel> getMoreTools(@Field("access_token") String access_token,
                                             @Field("tool_id") int tool_id,
                                             @Field("user_id") int user_id);

    @GET("api/sale/v1/get_more_tools")
    Call<NearByTulListingModel> getMoreToolsSales(@Query("access_token") String access_token,
                                                  @Query("id") int tool_id);


    @FormUrlEncoded
    @POST("booking/check_availability")
    Call<AvailbiltyModel> checkAvailbilty(@Field("access_token") String access_token,
                                          @Field("tool_id") int tool_id);

    @FormUrlEncoded
    @POST("booking/check_availability_by_date")
    Call<DemoModel> checkAvailbiltyByDate(@Field("access_token") String access_token,
                                          @Field("tool_id") int tool_id,
                                          @Field("selected_date") String selected_date,
                                          @Field("quantity") int quantity);

    @FormUrlEncoded
    @POST("booking/book_a_tool")
    Call<BookTulModel> bookATul(@Field("access_token") String access_token,
                                @Field("tool_id") int tool_id,
                                @Field("additional_price") String additional_price,
                                @Field("price") String price,
                                @Field("delivery_date") String delivery_date,
                                @Field("return_date") String return_date,
                                @Field("security_fee") String security_fee,
                                @Field("total_amount") String total_amount,
                                @Field("address") String address,
                                @Field("latitude") String latitude,
                                @Field("longitude") String longitude,
                                @Field("selected_date") String selected_date,
                                @Field("source_token") String source_token,
                                @Field("delivery_type") String delivery_type,
                                @Field("delivery_cost") String delivery_cost,
                                @Field("quantity") String quantity,
                                @Field("fee") String fee,
                                @Field("save_card") String save_card,
                                @Field("card_number") String card_number,
                                @Field("name_on_card") String name_on_card,
                                @Field("expiry_month") String expiry_month,
                                @Field("expiry_year") String expiry_year,
                                @Field("preferences") String preferences,
                                @Field("tool_quantity") int tool_quantity,
                                @Field("tool_address") String tool_address,
                                @Field("discount") String discount,
                                @Field("base_price") String base_price,
                                @Field("transaction_fee") String transaction_fee,
                                @Field("transaction_percentage") String transaction_percentage,
                                @Field("extra_charges") int extra_charges,
                                @Field("discount_percentage") String discount_percentage,
                                @Field("discount_days") String discount_days);

    @FormUrlEncoded
    @POST("/api/sale/v1/bookings/book_a_sale_tool")
    Call<SalesBookTulModel> buyATul(@Field("access_token") String access_token,
                                    @Field("id") int id,
                                    @Field("quantity") int quantity,
                                    @Field("edit_count") int edit_count,
                                    @Field("delivery") String delivery,
                                    @Field("delivery_type") String delivery_type,
                                    @Field("payment_mode") String payment_mode,
                                    @Field("delivery_cost") String delivery_cost,
                                    @Field("card_number") String card_number,
                                    @Field("name_on_card") String name_on_card,
                                    @Field("expiry_month") String expiry_month,
                                    @Field("expiry_year") String expiry_year,
                                    @Field("price") String price,
                                    @Field("total_amount") String total_amount,
                                    @Field("address") String address,
                                    @Field("latitude") String latitude,
                                    @Field("longitude") String longitude,
                                    @Field("source_token") String source_token,
                                    @Field("primary_currency") String primary_currency
    );


    @FormUrlEncoded
    @POST("booking/my_bookings")
    Call<MyBookingModel> getMyBookings(@Field("access_token") String access_token,
                                       @Field("offset") int offset);


    @FormUrlEncoded
    @POST("booking/tool_lent")
    Call<MyBookingModel> getTulLent(@Field("access_token") String access_token,
                                    @Field("offset") int offset);


    @Multipart
    @POST("deal/cancel_booking")
    Call<DemoModel> cancelBooking(@Part("access_token") RequestBody access_token,
                                  @Part("booking_id") RequestBody booking_id,
                                  @Part("tool_id") RequestBody tool_id,
                                  @Part("user_type") RequestBody user_type,
                                  @Part MultipartBody.Part signature);


    @Multipart
    @POST("deal/lender_hand_over")
    Call<DemoModel> markAsHandover(@Part("access_token") RequestBody access_token,
                                   @Part("booking_id") RequestBody booking_id,
                                   @Part("tool_id") RequestBody tool_id,
                                   @Part MultipartBody.Part signature);

    @Multipart
    @POST("deal/borrower_receive")
    Call<DemoModel> markAsReceivedByBorrower(@Part("access_token") RequestBody access_token,
                                             @Part("booking_id") RequestBody booking_id,
                                             @Part("tool_id") RequestBody tool_id,
                                             @Part MultipartBody.Part signature);

    @Multipart
    @POST("deal/borrower_return")
    Call<DemoModel> markAsReturnedByBorrower(@Part("access_token") RequestBody access_token,
                                             @Part("booking_id") RequestBody booking_id,
                                             @Part("tool_id") RequestBody tool_id,
                                             @Part MultipartBody.Part signature);

    @GET("api/sale/v1/booking_detail")
    Call<SalesBookingModel> getSalesBookingData(@Query("access_token") String access_token,
                                                @Query("id") String booking_id);


    @FormUrlEncoded
    @POST("api/v2/users/delete_account")
    Call<DemoModel> deleteAccount(@Field("access_token") String access_token);

    @Multipart
    @POST("deal/lender_receive")
    Call<DemoModel> markAsReceivedByLender(@Part("access_token") RequestBody access_token,
                                           @Part("booking_id") RequestBody booking_id,
                                           @Part("tool_id") RequestBody tool_id,
                                           @Part MultipartBody.Part signature);


    @FormUrlEncoded
    @POST("feedback/rating")
    Call<DemoModel> feedBackRating(@Field("access_token") String access_token,
                                   @Field("booking_id") String booking_id,
                                   @Field("tool_id") String tool_id,
                                   @Field("rating") int rating,
                                   @Field("review") String review,
                                   @Field("type") int type);

    @FormUrlEncoded
    @POST("api/sale/v1/ratings/rate_a_sale_tool")
    Call<DemoModel> feedBackSalesRating(@Field("access_token") String access_token,
                                        @Field("id") String booking_id,
                                        @Field("rating") int rating,
                                        @Field("review") String review,
                                        @Field("type") int type);

    @FormUrlEncoded
    @POST("booking/booking_activity")
    Call<PendingTaskModel> getPendingTasks(@Field("access_token") String access_token);


    @Multipart
    @POST("deal/confirm_cancel")
    Call<DemoModel> confirmCancel(@Part("access_token") RequestBody access_token,
                                  @Part("booking_id") RequestBody booking_id,
                                  @Part("tool_id") RequestBody tool_id,
                                  @Part MultipartBody.Part signature);

    @Multipart
    @POST("deal/borrower_not_receive")
    Call<DemoModel> markAsNotReceivedbyBorrower(@Part("access_token") RequestBody access_token,
                                                @Part("booking_id") RequestBody booking_id,
                                                @Part("tool_id") RequestBody tool_id,
                                                @Part MultipartBody.Part signature);

    @Multipart
    @POST("deal/lender_not_received")
    Call<DemoModel> markAsNotReceivedbyLender(@Part("access_token") RequestBody access_token,
                                              @Part("booking_id") RequestBody booking_id,
                                              @Part("tool_id") RequestBody tool_id,
                                              @Part MultipartBody.Part signature);

    @FormUrlEncoded
    @POST("history/lent_tools")
    Call<LentTulModel> getLentTul(@Field("access_token") String access_token,
                                  @Field("offset") int offset);


    @GET("api/sale/v1/sale_histories")
    Call<SalesHistoryTulDetailModel> sale_histories(@Query("access_token") String access_token,
                                                    @Query("type") String type,
                                                    @Query("page") int offset);


    @FormUrlEncoded
    @POST("history/rented_tools")
    Call<LentTulModel> getRentedTul(@Field("access_token") String access_token,
                                    @Field("offset") int offset);

    @FormUrlEncoded
    @POST("lender_dashboard/dashboard_dates")
    Call<DashboardDatesModel> getDashBoardDates(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("lender_dashboard/dashboard_data")
    Call<DashboardDatesModel> getParticularDateData(@Field("access_token") String access_token,
                                                    @Field("date") String date);

    @FormUrlEncoded
    @POST("lender_dashboard/statistics")
    Call<StatisticsModel> getLenderStatistics(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("lender_dashboard/tool_lents")
    Call<DashboardMyTulModel> getDashBoardMyTul(@Field("access_token") String access_token,
                                                @Field("offset") int offset,
                                                @Field("filter") int filter);

    @FormUrlEncoded
    @POST("booking/booking_by_id")
    Call<ViewTulModel> getTulDetailByBookingId(@Field("access_token") String access_token,
                                               @Field("booking_id") int booking_id);

    @FormUrlEncoded
    @POST("booking/push_activity")
    Call<BookTulModel> getPushDeatilBookingId(@Field("access_token") String access_token,
                                              @Field("booking_id") int booking_id,
                                              @Field("push_type") int push_type);

    @FormUrlEncoded
    @POST("tools/pause_status")
    Call<DemoModel> pauseTul(@Field("access_token") String access_token,
                             @Field("tool_id") int tool_id,
                             @Field("status") int status);

    @FormUrlEncoded
    @POST("tools/search_tool")
    Call<SearchResultModel> searchTul(@Field("access_token") String access_token,
                                      @Field("title") String title,
                                      @Field("category_id") int category_id,
                                      @Field("delivery_type") int delivery_type,
                                      @Field("max_price") int max_price,
                                      @Field("min_price") int min_price,
                                      @Field("availability") int availability,
                                      @Field("latitude") String latitude,
                                      @Field("longitude") String longitude,
                                      @Field("rating") int rating,
                                      @Field("distance") int distance);

    @FormUrlEncoded
    @POST("tools/search_tool")
    Call<NearByTulListingModel> searchTulByCategory(@Field("access_token") String access_token,
                                                    @Field("title") String title,
                                                    @Field("category_id") int category_id,
                                                    @Field("delivery_type") int delivery_type,
                                                    @Field("max_price") int max_price,
                                                    @Field("min_price") int min_price,
                                                    @Field("availability") int availability,
                                                    @Field("latitude") String latitude,
                                                    @Field("longitude") String longitude,
                                                    @Field("rating") int rating,
                                                    @Field("distance") int distance);

    @FormUrlEncoded
    @POST("users/resend_email")
    Call<DemoModel> resendEmail(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("users/resend_email")
    Call<DemoModel> resendEditEmail(@Field("access_token") String access_token, @Field("email") String email);

    @FormUrlEncoded
    @POST("tools/search_tool")
    Call<SignupModel> getUserDetail(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("notifications/notification_panel")
    Call<NotificationModel> getNotifications(@Field("access_token") String access_token);


    @FormUrlEncoded
    @POST("notifications/read_notification")
    Call<DemoModel> readNotifications(@Field("access_token") String access_token,
                                      @Field("notification_id") String notification_id);


    @FormUrlEncoded
    @POST("app_info/cancellation_policy")
    Call<CancellationPolicyModel> cancellationPolicy(@Field("access_token") String access_token);

    @FormUrlEncoded
    @POST("feedback/ratings_reviews")
    Call<ReviewRatingModel> reviewsRatings(@Field("access_token") String access_token,
                                           @Field("tool_id") int tool_id);


    @FormUrlEncoded
    @POST("notifications/dialog")
    Call<String> updateDialog(@Field("access_token") String access_token,
                              @Field("dialog_id") String dialog_id,
                              @Field("receiver_id") String receiver_id);

    @FormUrlEncoded
    @POST("users/change_password")
    Call<DemoModel> changePassword(@Field("access_token") String access_token,
                                   @Field("old_password") String old_password,
                                   @Field("new_password") String new_password);

    @GET("version")
    Call<VersionModel> getVersion();


    @FormUrlEncoded
    @POST("app_info/transaction_percentage")
    Call<TransactionModel> getTransactionPercentage(@Field("access_token") String access_token);


    @FormUrlEncoded
    @POST("/api/sale/v1/users/switch_profile")
    Call<SignupModel> switch_profile(@Field("access_token") String access_token, @Field("login_type") String login_type);

    @FormUrlEncoded
    @POST("deal/chat_block")
    Call<CheckChatModel> checkChat(@Field("access_token") String access_token,
                                   @Field("tool_id") String title);


    @FormUrlEncoded
    @POST("users/skip_email_verification")
    Call<ProfileModel> skipEmailverification(@Field("access_token") String access_token);


    @FormUrlEncoded
    @POST("deal/chat_block_in_list")
    Call<CheckChatModel> checkChatInList(@Field("access_token") String access_token,
                                         @Field("receiver_id") String title);

    @FormUrlEncoded
    @POST("deal/booking_chat_block")
    Call<CheckChatModel> checkBookingChat(@Field("access_token") String access_token,
                                          @Field("tool_id") String title,
                                          @Field("owner_id") String owner_id,
                                          @Field("user_id") String user_id);

}