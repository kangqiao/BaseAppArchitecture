package com.jzsec.broker.data.entity;

import java.util.List;

/**
 * Created by zhaopan on 2016/10/18.
 * e-mail: kangqiao610@gmail.com
 */

public class UserInfo {

    /**
     * id : 33939
     * bid : 00052ab71a8e9af9480857996db5650b
     * employee_id : null
     * name :
     * broker_level : 1
     * can_sale_fund : 0
     * can_broke : 1
     * sex : 0
     * nationalism :
     * birthday : 0000-00-00 00:00:00
     * validity_start : 0000-00-00 00:00:00
     * validity_end : 0000-00-00 00:00:00
     * issuing_authority :
     * identity_no : null
     * idcard_address :
     * post_code :
     * reason :
     * education :
     * avater :
     * desc : 我是您的专属经纪人，我承诺合法、合规地从事客户招揽和客户服务活动。
     * mobilephone : 18511611085
     * brokerage : 8.0E-4
     * password : 200820e3227815ed1756a6b531e7e0d2
     * is_certificate : 0
     * come_from : 1012
     * job :
     * status : 4
     * opt_status : 0
     * create_time : 2016-09-08 09:43:56
     * update_time : 2016-09-22 16:51:48
     * bind_time : 2016-09-08 12:54:30
     * check_state :
     * sac_id : 23423
     * sac_apply_count : 434
     * sac_apply_password : 24234
     * contract_status : 4
     * safety_mail :
     * safety_phone : 0
     * mail : dvihinok@hjj.jj
     * address : 容易好不好比比
     * last_upload_client : 4f7215f724af42d6aec40b5b5c0399ae
     * last_sync_client : a26db2a6c3b44301afe9994c70d043da
     * contract_timelimit : 0
     * bfund_id : 0
     * refresh_exam : 1
     * qrcode_attc :
     * fund_status : 1
     * id_audit_status : 2
     * sq_exam_status : 2
     * train_status : 1
     * sac_status : -3
     * compliance_status : 1
     * compliance_time : 0
     * nickname : 康
     * sq_exam_content :
     * cur_contract_id : 0
     * private_cc_id : null
     * client_id : kingbroker_00052ab71a8e9af9480857996db5650b
     * display_nick : 1
     * is_default_nickname : 1
     * gbos_status : 0
     * mute : 1
     * files_version_no : 08310047
     * files_create_time : 2016-09-08 11:58:55
     * interview_status : 2
     * last_operate_his : 0
     * authority : 0
     * t_change_flag : 0
     * entry_time : 2016-09-22 16:51:48
     * professional_level :
     * salary : 0
     * team_leader_type :
     * registerRank : 33939
     * cerInfo : [{"status":"1","cerType":"1","bucket":"jingjibao","current_name":"8c5a95fe46c6019b58006ff82fba25c8","downloadUrl":"http://jingjibao.ufile.ucloud.cn/8c5a95fe46c6019b58006ff82fba25c8"},{"status":"1","cerType":"2","bucket":"jingjibao","current_name":"29c3616756262cb01a753ffcf4f247a7","downloadUrl":"http://jingjibao.ufile.ucloud.cn/29c3616756262cb01a753ffcf4f247a7"},{"status":"1","cerType":"901","bucket":"jingjibao","current_name":"c8793e12b44d97a8ad4311a609b8f8a9","downloadUrl":"http://jingjibao.ufile.ucloud.cn/c8793e12b44d97a8ad4311a609b8f8a9"}]
     * contract_start_time : 2000-01-05
     * contract_end_time : 2000-01-05
     * b_future_id :
     * id_audit_last :
     * sq_exam_last :
     * train_last :
     * contract_last :
     * sac_last :
     * follow_last :
     */

    private int id;
    private String bid;
    private Object employee_id;
    private String name;
    private int broker_level;
    private int can_sale_fund;
    private int can_broke;
    private int sex;
    private String nationalism;
    private String birthday;
    private String validity_start;
    private String validity_end;
    private String issuing_authority;
    private Object identity_no;
    private String idcard_address;
    private String post_code;
    private String reason;
    private String education;
    private String avater;
    private String desc;
    private String mobilephone;
    private double brokerage;
    private String password;
    private int is_certificate;
    private int come_from;
    private String job;
    private int status;
    private int opt_status;
    private String create_time;
    private String update_time;
    private String bind_time;
    private String check_state;
    private String sac_id;
    private String sac_apply_count;
    private String sac_apply_password;
    private String contract_status;
    private String safety_mail;
    private int safety_phone;
    private String mail;
    private String address;
    private String last_upload_client;
    private String last_sync_client;
    private int contract_timelimit;
    private int bfund_id;
    private int refresh_exam;
    private String qrcode_attc;
    private int fund_status;
    private int id_audit_status;
    private int sq_exam_status;
    private int train_status;
    private int sac_status;
    private int compliance_status;
    private int compliance_time;
    private String nickname;
    private String sq_exam_content;
    private int cur_contract_id;
    private Object private_cc_id;
    private String client_id;
    private int display_nick;
    private int is_default_nickname;
    private int gbos_status;
    private int mute;
    private String files_version_no;
    private String files_create_time;
    private int interview_status;
    private int last_operate_his;
    private int authority;
    private int t_change_flag;
    private String entry_time;
    private String professional_level;
    private int salary;
    private String team_leader_type;
    private int registerRank;
    private String contract_start_time;
    private String contract_end_time;
    private String b_future_id;
    private String id_audit_last;
    private String sq_exam_last;
    private String train_last;
    private String contract_last;
    private String sac_last;
    private String follow_last;
    /**
     * status : 1
     * cerType : 1
     * bucket : jingjibao
     * current_name : 8c5a95fe46c6019b58006ff82fba25c8
     * downloadUrl : http://jingjibao.ufile.ucloud.cn/8c5a95fe46c6019b58006ff82fba25c8
     */

    private List<CerInfoBean> cerInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public Object getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Object employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBroker_level() {
        return broker_level;
    }

    public void setBroker_level(int broker_level) {
        this.broker_level = broker_level;
    }

    public int getCan_sale_fund() {
        return can_sale_fund;
    }

    public void setCan_sale_fund(int can_sale_fund) {
        this.can_sale_fund = can_sale_fund;
    }

    public int getCan_broke() {
        return can_broke;
    }

    public void setCan_broke(int can_broke) {
        this.can_broke = can_broke;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNationalism() {
        return nationalism;
    }

    public void setNationalism(String nationalism) {
        this.nationalism = nationalism;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getValidity_start() {
        return validity_start;
    }

    public void setValidity_start(String validity_start) {
        this.validity_start = validity_start;
    }

    public String getValidity_end() {
        return validity_end;
    }

    public void setValidity_end(String validity_end) {
        this.validity_end = validity_end;
    }

    public String getIssuing_authority() {
        return issuing_authority;
    }

    public void setIssuing_authority(String issuing_authority) {
        this.issuing_authority = issuing_authority;
    }

    public Object getIdentity_no() {
        return identity_no;
    }

    public void setIdentity_no(Object identity_no) {
        this.identity_no = identity_no;
    }

    public String getIdcard_address() {
        return idcard_address;
    }

    public void setIdcard_address(String idcard_address) {
        this.idcard_address = idcard_address;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public double getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(double brokerage) {
        this.brokerage = brokerage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIs_certificate() {
        return is_certificate;
    }

    public void setIs_certificate(int is_certificate) {
        this.is_certificate = is_certificate;
    }

    public int getCome_from() {
        return come_from;
    }

    public void setCome_from(int come_from) {
        this.come_from = come_from;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOpt_status() {
        return opt_status;
    }

    public void setOpt_status(int opt_status) {
        this.opt_status = opt_status;
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

    public String getBind_time() {
        return bind_time;
    }

    public void setBind_time(String bind_time) {
        this.bind_time = bind_time;
    }

    public String getCheck_state() {
        return check_state;
    }

    public void setCheck_state(String check_state) {
        this.check_state = check_state;
    }

    public String getSac_id() {
        return sac_id;
    }

    public void setSac_id(String sac_id) {
        this.sac_id = sac_id;
    }

    public String getSac_apply_count() {
        return sac_apply_count;
    }

    public void setSac_apply_count(String sac_apply_count) {
        this.sac_apply_count = sac_apply_count;
    }

    public String getSac_apply_password() {
        return sac_apply_password;
    }

    public void setSac_apply_password(String sac_apply_password) {
        this.sac_apply_password = sac_apply_password;
    }

    public String getContract_status() {
        return contract_status;
    }

    public void setContract_status(String contract_status) {
        this.contract_status = contract_status;
    }

    public String getSafety_mail() {
        return safety_mail;
    }

    public void setSafety_mail(String safety_mail) {
        this.safety_mail = safety_mail;
    }

    public int getSafety_phone() {
        return safety_phone;
    }

    public void setSafety_phone(int safety_phone) {
        this.safety_phone = safety_phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLast_upload_client() {
        return last_upload_client;
    }

    public void setLast_upload_client(String last_upload_client) {
        this.last_upload_client = last_upload_client;
    }

    public String getLast_sync_client() {
        return last_sync_client;
    }

    public void setLast_sync_client(String last_sync_client) {
        this.last_sync_client = last_sync_client;
    }

    public int getContract_timelimit() {
        return contract_timelimit;
    }

    public void setContract_timelimit(int contract_timelimit) {
        this.contract_timelimit = contract_timelimit;
    }

    public int getBfund_id() {
        return bfund_id;
    }

    public void setBfund_id(int bfund_id) {
        this.bfund_id = bfund_id;
    }

    public int getRefresh_exam() {
        return refresh_exam;
    }

    public void setRefresh_exam(int refresh_exam) {
        this.refresh_exam = refresh_exam;
    }

    public String getQrcode_attc() {
        return qrcode_attc;
    }

    public void setQrcode_attc(String qrcode_attc) {
        this.qrcode_attc = qrcode_attc;
    }

    public int getFund_status() {
        return fund_status;
    }

    public void setFund_status(int fund_status) {
        this.fund_status = fund_status;
    }

    public int getId_audit_status() {
        return id_audit_status;
    }

    public void setId_audit_status(int id_audit_status) {
        this.id_audit_status = id_audit_status;
    }

    public int getSq_exam_status() {
        return sq_exam_status;
    }

    public void setSq_exam_status(int sq_exam_status) {
        this.sq_exam_status = sq_exam_status;
    }

    public int getTrain_status() {
        return train_status;
    }

    public void setTrain_status(int train_status) {
        this.train_status = train_status;
    }

    public int getSac_status() {
        return sac_status;
    }

    public void setSac_status(int sac_status) {
        this.sac_status = sac_status;
    }

    public int getCompliance_status() {
        return compliance_status;
    }

    public void setCompliance_status(int compliance_status) {
        this.compliance_status = compliance_status;
    }

    public int getCompliance_time() {
        return compliance_time;
    }

    public void setCompliance_time(int compliance_time) {
        this.compliance_time = compliance_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSq_exam_content() {
        return sq_exam_content;
    }

    public void setSq_exam_content(String sq_exam_content) {
        this.sq_exam_content = sq_exam_content;
    }

    public int getCur_contract_id() {
        return cur_contract_id;
    }

    public void setCur_contract_id(int cur_contract_id) {
        this.cur_contract_id = cur_contract_id;
    }

    public Object getPrivate_cc_id() {
        return private_cc_id;
    }

    public void setPrivate_cc_id(Object private_cc_id) {
        this.private_cc_id = private_cc_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public int getDisplay_nick() {
        return display_nick;
    }

    public void setDisplay_nick(int display_nick) {
        this.display_nick = display_nick;
    }

    public int getIs_default_nickname() {
        return is_default_nickname;
    }

    public void setIs_default_nickname(int is_default_nickname) {
        this.is_default_nickname = is_default_nickname;
    }

    public int getGbos_status() {
        return gbos_status;
    }

    public void setGbos_status(int gbos_status) {
        this.gbos_status = gbos_status;
    }

    public int getMute() {
        return mute;
    }

    public void setMute(int mute) {
        this.mute = mute;
    }

    public String getFiles_version_no() {
        return files_version_no;
    }

    public void setFiles_version_no(String files_version_no) {
        this.files_version_no = files_version_no;
    }

    public String getFiles_create_time() {
        return files_create_time;
    }

    public void setFiles_create_time(String files_create_time) {
        this.files_create_time = files_create_time;
    }

    public int getInterview_status() {
        return interview_status;
    }

    public void setInterview_status(int interview_status) {
        this.interview_status = interview_status;
    }

    public int getLast_operate_his() {
        return last_operate_his;
    }

    public void setLast_operate_his(int last_operate_his) {
        this.last_operate_his = last_operate_his;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public int getT_change_flag() {
        return t_change_flag;
    }

    public void setT_change_flag(int t_change_flag) {
        this.t_change_flag = t_change_flag;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }

    public String getProfessional_level() {
        return professional_level;
    }

    public void setProfessional_level(String professional_level) {
        this.professional_level = professional_level;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getTeam_leader_type() {
        return team_leader_type;
    }

    public void setTeam_leader_type(String team_leader_type) {
        this.team_leader_type = team_leader_type;
    }

    public int getRegisterRank() {
        return registerRank;
    }

    public void setRegisterRank(int registerRank) {
        this.registerRank = registerRank;
    }

    public String getContract_start_time() {
        return contract_start_time;
    }

    public void setContract_start_time(String contract_start_time) {
        this.contract_start_time = contract_start_time;
    }

    public String getContract_end_time() {
        return contract_end_time;
    }

    public void setContract_end_time(String contract_end_time) {
        this.contract_end_time = contract_end_time;
    }

    public String getB_future_id() {
        return b_future_id;
    }

    public void setB_future_id(String b_future_id) {
        this.b_future_id = b_future_id;
    }

    public String getId_audit_last() {
        return id_audit_last;
    }

    public void setId_audit_last(String id_audit_last) {
        this.id_audit_last = id_audit_last;
    }

    public String getSq_exam_last() {
        return sq_exam_last;
    }

    public void setSq_exam_last(String sq_exam_last) {
        this.sq_exam_last = sq_exam_last;
    }

    public String getTrain_last() {
        return train_last;
    }

    public void setTrain_last(String train_last) {
        this.train_last = train_last;
    }

    public String getContract_last() {
        return contract_last;
    }

    public void setContract_last(String contract_last) {
        this.contract_last = contract_last;
    }

    public String getSac_last() {
        return sac_last;
    }

    public void setSac_last(String sac_last) {
        this.sac_last = sac_last;
    }

    public String getFollow_last() {
        return follow_last;
    }

    public void setFollow_last(String follow_last) {
        this.follow_last = follow_last;
    }

    public List<CerInfoBean> getCerInfo() {
        return cerInfo;
    }

    public void setCerInfo(List<CerInfoBean> cerInfo) {
        this.cerInfo = cerInfo;
    }

    public static class CerInfoBean {
        private String status;
        private String cerType;
        private String bucket;
        private String current_name;
        private String downloadUrl;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCerType() {
            return cerType;
        }

        public void setCerType(String cerType) {
            this.cerType = cerType;
        }

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        public String getCurrent_name() {
            return current_name;
        }

        public void setCurrent_name(String current_name) {
            this.current_name = current_name;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", bid='" + bid + '\'' +
                ", employee_id=" + employee_id +
                ", name='" + name + '\'' +
                ", broker_level=" + broker_level +
                ", can_sale_fund=" + can_sale_fund +
                ", can_broke=" + can_broke +
                ", sex=" + sex +
                ", nationalism='" + nationalism + '\'' +
                ", birthday='" + birthday + '\'' +
                ", validity_start='" + validity_start + '\'' +
                ", validity_end='" + validity_end + '\'' +
                ", issuing_authority='" + issuing_authority + '\'' +
                ", identity_no=" + identity_no +
                ", idcard_address='" + idcard_address + '\'' +
                ", post_code='" + post_code + '\'' +
                ", reason='" + reason + '\'' +
                ", education='" + education + '\'' +
                ", avater='" + avater + '\'' +
                ", desc='" + desc + '\'' +
                ", mobilephone='" + mobilephone + '\'' +
                ", brokerage=" + brokerage +
                ", password='" + password + '\'' +
                ", is_certificate=" + is_certificate +
                ", come_from=" + come_from +
                ", job='" + job + '\'' +
                ", status=" + status +
                ", opt_status=" + opt_status +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", bind_time='" + bind_time + '\'' +
                ", check_state='" + check_state + '\'' +
                ", sac_id='" + sac_id + '\'' +
                ", sac_apply_count='" + sac_apply_count + '\'' +
                ", sac_apply_password='" + sac_apply_password + '\'' +
                ", contract_status='" + contract_status + '\'' +
                ", safety_mail='" + safety_mail + '\'' +
                ", safety_phone=" + safety_phone +
                ", mail='" + mail + '\'' +
                ", address='" + address + '\'' +
                ", last_upload_client='" + last_upload_client + '\'' +
                ", last_sync_client='" + last_sync_client + '\'' +
                ", contract_timelimit=" + contract_timelimit +
                ", bfund_id=" + bfund_id +
                ", refresh_exam=" + refresh_exam +
                ", qrcode_attc='" + qrcode_attc + '\'' +
                ", fund_status=" + fund_status +
                ", id_audit_status=" + id_audit_status +
                ", sq_exam_status=" + sq_exam_status +
                ", train_status=" + train_status +
                ", sac_status=" + sac_status +
                ", compliance_status=" + compliance_status +
                ", compliance_time=" + compliance_time +
                ", nickname='" + nickname + '\'' +
                ", sq_exam_content='" + sq_exam_content + '\'' +
                ", cur_contract_id=" + cur_contract_id +
                ", private_cc_id=" + private_cc_id +
                ", client_id='" + client_id + '\'' +
                ", display_nick=" + display_nick +
                ", is_default_nickname=" + is_default_nickname +
                ", gbos_status=" + gbos_status +
                ", mute=" + mute +
                ", files_version_no='" + files_version_no + '\'' +
                ", files_create_time='" + files_create_time + '\'' +
                ", interview_status=" + interview_status +
                ", last_operate_his=" + last_operate_his +
                ", authority=" + authority +
                ", t_change_flag=" + t_change_flag +
                ", entry_time='" + entry_time + '\'' +
                ", professional_level='" + professional_level + '\'' +
                ", salary=" + salary +
                ", team_leader_type='" + team_leader_type + '\'' +
                ", registerRank=" + registerRank +
                ", contract_start_time='" + contract_start_time + '\'' +
                ", contract_end_time='" + contract_end_time + '\'' +
                ", b_future_id='" + b_future_id + '\'' +
                ", id_audit_last='" + id_audit_last + '\'' +
                ", sq_exam_last='" + sq_exam_last + '\'' +
                ", train_last='" + train_last + '\'' +
                ", contract_last='" + contract_last + '\'' +
                ", sac_last='" + sac_last + '\'' +
                ", follow_last='" + follow_last + '\'' +
                ", cerInfo=" + cerInfo +
                '}';
    }
}
