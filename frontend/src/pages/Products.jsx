import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function Products() {

    const [products, setProducts] = useState([]);
const [recommended, setRecommended] = useState([]);
const [filteredProducts, setFilteredProducts] = useState([]);

const [search, setSearch] = useState("");
const [cartMessage, setCartMessage] = useState("");
const [aiRecommendation, setAiRecommendation] = useState("");

const navigate = useNavigate();

    useEffect(() => {

        api.get("/products")
            .then((res) => {
                setProducts(res.data);
                setFilteredProducts(res.data);
            })
            .catch(err => console.log(err));


        

    }, []);



   const handleSearch = () => {

    const result = products.filter(product =>
        product.name
            .toLowerCase()
            .includes(search.toLowerCase())
    );

    setFilteredProducts(result);

    if (search.trim() !== "") {

        api.get("/ai/recommend", {
            params: {
                userNeed: search
            }
        })
       .then((res) => {
    setRecommended(
        Array.isArray(res.data) ? res.data : [res.data]
    );
})
.catch((err) => {
    console.log(err);
    setRecommended([]);
});

    }
};


    const clearSearch = () => {

        setSearch("");
        setFilteredProducts(products);

    };



    const addToCart = (product) => {

    console.log("PRODUCT:", product);
    console.log("PRODUCT ID:", product?.id);

    if (!product || product.id === undefined || product.id === null) {
        console.error("PRODUCT ID IS MISSING:", product);
        alert("Cannot add this product because Product ID is missing.");
        return;
    }

    const userId = Number(localStorage.getItem("userId"));

    if (!userId) {
        alert("Please login first.");
        return;
    }

    const cartData = {
        userId: userId,
        productId: Number(product.id),
        quantity: 1
    };

    console.log("CART DATA:", cartData);

    api.post("/cart", cartData)
        .then((res) => {

            console.log("CART SUCCESS:", res.data);

            setCartMessage(
                product.name + " added to cart 🛒"
            );

            setTimeout(() => {
                setCartMessage("");
            }, 2000);

        })
        .catch((err) => {

            console.log("CART ERROR:", err);

            if (err.response) {
                console.log(
                    "SERVER RESPONSE:",
                    err.response.data
                );
            }

            alert("Failed to add cart");
        });
};

return (
        <div style={styles.page}>


            <div style={styles.navbar}>

                <h1 style={styles.logo}>
                    Amazon AI
                </h1>


                <div>

                    <button
                        style={styles.navButton}
                        onClick={() => navigate("/cart")}
                    >
                        🛒 Cart
                    </button>


                    <button
                        style={styles.navButton}
                        onClick={() => navigate("/orders")}
                    >
                        📦 Orders
                    </button>

                </div>

            </div>



            <div style={styles.searchBox}>


                <input

                    style={styles.search}

                    placeholder="🔍 Search products..."

                    value={search}

                    onChange={(e) => setSearch(e.target.value)}

                />


                <button
                    style={styles.searchButton}
                    onClick={handleSearch}
                >
                    Search
                </button>


                <button
                    style={styles.clearButton}
                    onClick={clearSearch}
                >
                    Clear
                </button>


            </div>



           <div style={styles.message}>
    {cartMessage}
</div>

{aiRecommendation && (
    <div style={styles.aiRecommendation}>
        <h2>🤖 AI Recommendation</h2>
        <p>{aiRecommendation}</p>
    </div>
)}




            {
                search !== "" && (

                    <>

                        <h2 style={styles.title}>
                            🤖 Recommended For You
                        </h2>


                        <div style={styles.container}>

                            {
                                recommended.map((product, index) => (

                                    <div
                                        key={product.id || index}
                                        style={styles.card}
                                    >

                                        <div style={styles.aiImage}>
                                            🤖
                                        </div>


                                        <h2>
                                            {product.name}
                                        </h2>


                                        <p>
                                            {product.description}
                                        </p>


                                        <h3 style={styles.price}>
                                            ₹ {product.price}
                                        </h3>


                                        <button
                                            style={styles.cartButton}
                                            onClick={() => addToCart(product)}
                                        >
                                            🛒 Add to Cart
                                        </button>


                                    </div>

                                ))
                            }

                        </div>

                    </>

                )
            }




            <h2 style={styles.title}>
                🛍️ All Products
            </h2>



            <div style={styles.container}>


                {
                    filteredProducts.map(product => (

                        <div
                            key={product.id || index}
                            style={styles.card}
                        >

                            <div style={styles.image}>
                                🛍️
                            </div>


                            <h2>
                                {product.name}
                            </h2>


                            <p>
                                {product.description}
                            </p>


                            <h3 style={styles.price}>
                                ₹ {product.price}
                            </h3>


                            <button
                                style={styles.cartButton}
                                onClick={() => addToCart(product)}
                            >
                                🛒 Add to Cart
                            </button>


                        </div>

                    ))
                }


            </div>



        </div>

    );

}



const styles = {


page:{
    minHeight:"100vh",
    background:"#eaeded"
},


navbar:{
    background:"#131921",
    padding:"20px 40px",
    display:"flex",
    justifyContent:"space-between",
    alignItems:"center"
},


logo:{
    color:"#ff9900",
    fontSize:"35px"
},


navButton:{
    marginLeft:"15px",
    padding:"12px 22px",
    border:"none",
    borderRadius:"8px",
    cursor:"pointer"
},


searchBox:{
    display:"flex",
    justifyContent:"center",
    gap:"10px",
    margin:"30px"
},


search:{
    width:"450px",
    padding:"15px",
    borderRadius:"30px"
},


searchButton:{
    background:"#ff9900",
    padding:"15px 25px",
    border:"none",
    borderRadius:"25px"
},


clearButton:{
    padding:"15px 25px",
    border:"none",
    borderRadius:"25px"
},


message:{
    textAlign:"center",
    padding:"10px"
},

aiRecommendation:{
    background:"#fff3cd",
    margin:"20px 30px",
    padding:"20px",
    borderRadius:"15px",
    boxShadow:"0 3px 10px rgba(0,0,0,0.15)",
    textAlign:"center"
},

title:{
    textAlign:"center",
    color:"#232f3e"
},


container:{
    display:"grid",
    gridTemplateColumns:"repeat(auto-fit,minmax(280px,1fr))",
    gap:"30px",
    padding:"30px"
},


card:{
    background:"white",
    padding:"25px",
    borderRadius:"15px",
    boxShadow:"0 5px 20px rgba(0,0,0,0.15)"
},


image:{
    height:"150px",
    display:"flex",
    justifyContent:"center",
    alignItems:"center",
    fontSize:"70px"
},


aiImage:{
    height:"150px",
    background:"#fff3cd",
    display:"flex",
    justifyContent:"center",
    alignItems:"center",
    fontSize:"70px"
},


price:{
    color:"#b12704"
},


cartButton:{
    width:"100%",
    background:"#ff9900",
    padding:"14px",
    border:"none",
    borderRadius:"25px",
    cursor:"pointer"
}


};


export default Products;