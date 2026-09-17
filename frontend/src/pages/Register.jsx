import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";


function Register() {

    const navigate = useNavigate();

    const [name,setName] = useState("");
    const [email,setEmail] = useState("");
    const [password,setPassword] = useState("");


    const register = async(e)=>{
        e.preventDefault();

        try{

            await api.post("/auth/register",{
                name,
                email,
                password,
                role:"USER"
            });

            alert("Registration successful");

            navigate("/login");

        }
        catch(error){

            alert("Registration failed");

        }
    };


    return(

        <div style={styles.page}>

            <div style={styles.card}>

                <h1 style={styles.logo}>
                    Amazon AI
                </h1>

                <p style={styles.subtitle}>
                    Create your smart shopping account
                </p>


                <form onSubmit={register}>


                    <input
                    style={styles.input}
                    placeholder="Full Name"
                    value={name}
                    onChange={(e)=>setName(e.target.value)}
                    />


                    <input
                    style={styles.input}
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e)=>setEmail(e.target.value)}
                    />


                    <input
                    style={styles.input}
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e)=>setPassword(e.target.value)}
                    />


                    <button style={styles.button}>
                        Create Account
                    </button>


                </form>


                <p style={styles.bottom}>
                    Join Amazon AI 🚀
                </p>


            </div>

        </div>

    );

}



const styles={

page:{
    height:"100vh",
    display:"flex",
    justifyContent:"center",
    alignItems:"center",
    background:
    "linear-gradient(135deg,#131921,#ff9900)"
},


card:{
    width:"380px",
    padding:"40px",
    background:"white",
    borderRadius:"15px",
    boxShadow:"0 10px 30px rgba(0,0,0,0.3)",
    textAlign:"center"
},


logo:{
    color:"#ff9900",
    fontSize:"38px",
    marginBottom:"5px"
},


subtitle:{
    color:"#555",
    marginBottom:"25px"
},


input:{
    width:"100%",
    padding:"14px",
    margin:"10px 0",
    borderRadius:"8px",
    border:"1px solid #ccc",
    fontSize:"16px",
    boxSizing:"border-box"
},


button:{
    width:"100%",
    padding:"14px",
    marginTop:"15px",
    background:"#ff9900",
    border:"none",
    color:"white",
    borderRadius:"8px",
    fontSize:"18px",
    cursor:"pointer"
},


bottom:{
    marginTop:"20px",
    color:"#777"
}

};


export default Register;