package com.jzsec.broker.data.entity;


import java.util.List;

/**
 * Created by zhaopan on 2016/10/18.
 * e-mail: kangqiao610@gmail.com
 */
public class LoginInfo {

    /**
     * userId : 00052ab71a8e9af9480857996db5650b
     * token : dc553a0f6b80c2784ec1f862d0f761bc
     * bUserCount : 33939
     * regStep : 4
     * allBroker : 33993
     * contStatus : 2
     * qrcode :
     * roleName : 北京分公司
     * role_id : 22
     * roles : [{"id":"897","type":"2","user_id":"00052ab71a8e9af9480857996db5650b","role_id":"22","status":"1","post_id":"0","create_time":"2016-09-08 10:27:29","update_time":"2016-09-08 10:27:29","role_name":"北京分公司","roleInfo":{"id":"22","sidi_id":"0831","name":"北京分公司","contact":"","parent_id":"1","description":"","status":"1","province":{"id":"2","parent_id":"0","area_name":"北京","zipcode":"0"},"city":{"id":"36","parent_id":"2","area_name":"北京市","zipcode":"0"},"country":"1","district":{"id":"381","parent_id":"36","area_name":"朝阳区","zipcode":"100020"},"address":"北京市朝阳区安立路30号仰山公园东一门2号楼","is_department":"1","is_subcmp":"1","is_invest":"0","is_futurecmp":"0","order":"10","create_time":"2015-10-30 13:30:38","update_time":"2016-09-27 18:08:40","longitude":"116.41285428","latitude":"40.01663649","pic_attc":"2249dbc4d1ea31050cba7f80036846b0","mngname":"潘禹光","mngrole":"运营总监","mngpic_attc":"4420f49c506267e9dab3af28060ce053","mngmobile":"18510488323","mngmail":"panyuguang@jzsec.com","mngqrcode_attc":"8baef049196747502fc9d8ce7067d4eb","mngdesc":"踏实、专注、责任心强，相信目标的达成在于持续的积累，成功没有捷径。爱好广泛，喜欢历史、热爱运动。","mngwechat":"panyuguang522","estimate_cost_line":"0","province_name":"北京","city_name":"北京市","district_name":"朝阳区"}}]
     */

    private String userId;
    private String token;
    private int bUserCount;
    private int regStep;
    private String allBroker;
    private int contStatus;
    private String qrcode;
    private String roleName;
    private String role_id;
    /**
     * id : 897
     * type : 2
     * user_id : 00052ab71a8e9af9480857996db5650b
     * role_id : 22
     * status : 1
     * post_id : 0
     * create_time : 2016-09-08 10:27:29
     * update_time : 2016-09-08 10:27:29
     * role_name : 北京分公司
     * roleInfo : {"id":"22","sidi_id":"0831","name":"北京分公司","contact":"","parent_id":"1","description":"","status":"1","province":{"id":"2","parent_id":"0","area_name":"北京","zipcode":"0"},"city":{"id":"36","parent_id":"2","area_name":"北京市","zipcode":"0"},"country":"1","district":{"id":"381","parent_id":"36","area_name":"朝阳区","zipcode":"100020"},"address":"北京市朝阳区安立路30号仰山公园东一门2号楼","is_department":"1","is_subcmp":"1","is_invest":"0","is_futurecmp":"0","order":"10","create_time":"2015-10-30 13:30:38","update_time":"2016-09-27 18:08:40","longitude":"116.41285428","latitude":"40.01663649","pic_attc":"2249dbc4d1ea31050cba7f80036846b0","mngname":"潘禹光","mngrole":"运营总监","mngpic_attc":"4420f49c506267e9dab3af28060ce053","mngmobile":"18510488323","mngmail":"panyuguang@jzsec.com","mngqrcode_attc":"8baef049196747502fc9d8ce7067d4eb","mngdesc":"踏实、专注、责任心强，相信目标的达成在于持续的积累，成功没有捷径。爱好广泛，喜欢历史、热爱运动。","mngwechat":"panyuguang522","estimate_cost_line":"0","province_name":"北京","city_name":"北京市","district_name":"朝阳区"}
     */

    private List<RolesBean> roles;

    private UserInfo userInfo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getBUserCount() {
        return bUserCount;
    }

    public void setBUserCount(int bUserCount) {
        this.bUserCount = bUserCount;
    }

    public int getRegStep() {
        return regStep;
    }

    public void setRegStep(int regStep) {
        this.regStep = regStep;
    }

    public String getAllBroker() {
        return allBroker;
    }

    public void setAllBroker(String allBroker) {
        this.allBroker = allBroker;
    }

    public int getContStatus() {
        return contStatus;
    }

    public void setContStatus(int contStatus) {
        this.contStatus = contStatus;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public List<RolesBean> getRoles() {
        return roles;
    }

    public void setRoles(List<RolesBean> roles) {
        this.roles = roles;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public static class RolesBean {
        private String id;
        private String type;
        private String user_id;
        private String role_id;
        private String status;
        private String post_id;
        private String create_time;
        private String update_time;
        private String role_name;
        /**
         * id : 22
         * sidi_id : 0831
         * name : 北京分公司
         * contact :
         * parent_id : 1
         * description :
         * status : 1
         * province : {"id":"2","parent_id":"0","area_name":"北京","zipcode":"0"}
         * city : {"id":"36","parent_id":"2","area_name":"北京市","zipcode":"0"}
         * country : 1
         * district : {"id":"381","parent_id":"36","area_name":"朝阳区","zipcode":"100020"}
         * address : 北京市朝阳区安立路30号仰山公园东一门2号楼
         * is_department : 1
         * is_subcmp : 1
         * is_invest : 0
         * is_futurecmp : 0
         * order : 10
         * create_time : 2015-10-30 13:30:38
         * update_time : 2016-09-27 18:08:40
         * longitude : 116.41285428
         * latitude : 40.01663649
         * pic_attc : 2249dbc4d1ea31050cba7f80036846b0
         * mngname : 潘禹光
         * mngrole : 运营总监
         * mngpic_attc : 4420f49c506267e9dab3af28060ce053
         * mngmobile : 18510488323
         * mngmail : panyuguang@jzsec.com
         * mngqrcode_attc : 8baef049196747502fc9d8ce7067d4eb
         * mngdesc : 踏实、专注、责任心强，相信目标的达成在于持续的积累，成功没有捷径。爱好广泛，喜欢历史、热爱运动。
         * mngwechat : panyuguang522
         * estimate_cost_line : 0
         * province_name : 北京
         * city_name : 北京市
         * district_name : 朝阳区
         */

        private RoleInfoBean roleInfo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPost_id() {
            return post_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        public RoleInfoBean getRoleInfo() {
            return roleInfo;
        }

        public void setRoleInfo(RoleInfoBean roleInfo) {
            this.roleInfo = roleInfo;
        }

        public static class RoleInfoBean {
            private String id;
            private String sidi_id;
            private String name;
            private String contact;
            private String parent_id;
            private String description;
            private String status;
            /**
             * id : 2
             * parent_id : 0
             * area_name : 北京
             * zipcode : 0
             */

            private ProvinceBean province;
            /**
             * id : 36
             * parent_id : 2
             * area_name : 北京市
             * zipcode : 0
             */

            private CityBean city;
            private String country;
            /**
             * id : 381
             * parent_id : 36
             * area_name : 朝阳区
             * zipcode : 100020
             */

            private DistrictBean district;
            private String address;
            private String is_department;
            private String is_subcmp;
            private String is_invest;
            private String is_futurecmp;
            private String order;
            private String create_time;
            private String update_time;
            private String longitude;
            private String latitude;
            private String pic_attc;
            private String mngname;
            private String mngrole;
            private String mngpic_attc;
            private String mngmobile;
            private String mngmail;
            private String mngqrcode_attc;
            private String mngdesc;
            private String mngwechat;
            private String estimate_cost_line;
            private String province_name;
            private String city_name;
            private String district_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSidi_id() {
                return sidi_id;
            }

            public void setSidi_id(String sidi_id) {
                this.sidi_id = sidi_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public ProvinceBean getProvince() {
                return province;
            }

            public void setProvince(ProvinceBean province) {
                this.province = province;
            }

            public CityBean getCity() {
                return city;
            }

            public void setCity(CityBean city) {
                this.city = city;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public DistrictBean getDistrict() {
                return district;
            }

            public void setDistrict(DistrictBean district) {
                this.district = district;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getIs_department() {
                return is_department;
            }

            public void setIs_department(String is_department) {
                this.is_department = is_department;
            }

            public String getIs_subcmp() {
                return is_subcmp;
            }

            public void setIs_subcmp(String is_subcmp) {
                this.is_subcmp = is_subcmp;
            }

            public String getIs_invest() {
                return is_invest;
            }

            public void setIs_invest(String is_invest) {
                this.is_invest = is_invest;
            }

            public String getIs_futurecmp() {
                return is_futurecmp;
            }

            public void setIs_futurecmp(String is_futurecmp) {
                this.is_futurecmp = is_futurecmp;
            }

            public String getOrder() {
                return order;
            }

            public void setOrder(String order) {
                this.order = order;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getPic_attc() {
                return pic_attc;
            }

            public void setPic_attc(String pic_attc) {
                this.pic_attc = pic_attc;
            }

            public String getMngname() {
                return mngname;
            }

            public void setMngname(String mngname) {
                this.mngname = mngname;
            }

            public String getMngrole() {
                return mngrole;
            }

            public void setMngrole(String mngrole) {
                this.mngrole = mngrole;
            }

            public String getMngpic_attc() {
                return mngpic_attc;
            }

            public void setMngpic_attc(String mngpic_attc) {
                this.mngpic_attc = mngpic_attc;
            }

            public String getMngmobile() {
                return mngmobile;
            }

            public void setMngmobile(String mngmobile) {
                this.mngmobile = mngmobile;
            }

            public String getMngmail() {
                return mngmail;
            }

            public void setMngmail(String mngmail) {
                this.mngmail = mngmail;
            }

            public String getMngqrcode_attc() {
                return mngqrcode_attc;
            }

            public void setMngqrcode_attc(String mngqrcode_attc) {
                this.mngqrcode_attc = mngqrcode_attc;
            }

            public String getMngdesc() {
                return mngdesc;
            }

            public void setMngdesc(String mngdesc) {
                this.mngdesc = mngdesc;
            }

            public String getMngwechat() {
                return mngwechat;
            }

            public void setMngwechat(String mngwechat) {
                this.mngwechat = mngwechat;
            }

            public String getEstimate_cost_line() {
                return estimate_cost_line;
            }

            public void setEstimate_cost_line(String estimate_cost_line) {
                this.estimate_cost_line = estimate_cost_line;
            }

            public String getProvince_name() {
                return province_name;
            }

            public void setProvince_name(String province_name) {
                this.province_name = province_name;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public String getDistrict_name() {
                return district_name;
            }

            public void setDistrict_name(String district_name) {
                this.district_name = district_name;
            }

            public static class ProvinceBean {
                private String id;
                private String parent_id;
                private String area_name;
                private String zipcode;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getParent_id() {
                    return parent_id;
                }

                public void setParent_id(String parent_id) {
                    this.parent_id = parent_id;
                }

                public String getArea_name() {
                    return area_name;
                }

                public void setArea_name(String area_name) {
                    this.area_name = area_name;
                }

                public String getZipcode() {
                    return zipcode;
                }

                public void setZipcode(String zipcode) {
                    this.zipcode = zipcode;
                }
            }

            public static class CityBean {
                private String id;
                private String parent_id;
                private String area_name;
                private String zipcode;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getParent_id() {
                    return parent_id;
                }

                public void setParent_id(String parent_id) {
                    this.parent_id = parent_id;
                }

                public String getArea_name() {
                    return area_name;
                }

                public void setArea_name(String area_name) {
                    this.area_name = area_name;
                }

                public String getZipcode() {
                    return zipcode;
                }

                public void setZipcode(String zipcode) {
                    this.zipcode = zipcode;
                }
            }

            public static class DistrictBean {
                private String id;
                private String parent_id;
                private String area_name;
                private String zipcode;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getParent_id() {
                    return parent_id;
                }

                public void setParent_id(String parent_id) {
                    this.parent_id = parent_id;
                }

                public String getArea_name() {
                    return area_name;
                }

                public void setArea_name(String area_name) {
                    this.area_name = area_name;
                }

                public String getZipcode() {
                    return zipcode;
                }

                public void setZipcode(String zipcode) {
                    this.zipcode = zipcode;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", bUserCount=" + bUserCount +
                ", regStep=" + regStep +
                ", allBroker='" + allBroker + '\'' +
                ", contStatus=" + contStatus +
                ", qrcode='" + qrcode + '\'' +
                ", roleName='" + roleName + '\'' +
                ", role_id='" + role_id + '\'' +
                ", roles=" + roles +
                ", userInfo=" + userInfo +
                '}';
    }
}
