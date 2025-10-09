import React, { useState, useEffect } from "react";
import "./App.css";

function App() {
  const [data, setData] = useState([]);
  const [dataType, setDataType] = useState("Movies"); // The label for the current view
  const [currentEndpoint, setCurrentEndpoint] = useState("listMovies"); // The endpoint for the current view
  const [userLocation, setUserLocation] = useState("Rajamahendravaram"); // User's location state
  const [searchText, setSearchText] = useState("Rajamahendravaram"); // Temp state for search input
  
  const backend = "http://localhost:8080";

  // --- Main data fetching function ---
  const fetchData = async (endpoint, label) => {
    if (!userLocation) {
        alert("Please enter a location to search.");
        return;
    }
    try {
      setDataType(label);
      setCurrentEndpoint(endpoint);
      setData([]);

      const url = `${backend}/${endpoint}?location=${encodeURIComponent(userLocation)}`;
      const res = await fetch(url);
      const json = await res.json();
      setData(json);

    } catch (err) {
      console.error("Error fetching " + label, err);
      alert(`Could not fetch ${label}. Is the backend server running?`);
    }
  };
  
  // -- Load initial data on first render or when userLocation changes --
  useEffect(() => {
    fetchData(currentEndpoint, dataType);
  }, [userLocation]);

  const handleSearch = () => {
    setUserLocation(searchText);
  };

  const handleCategoryClick = (endpoint, label) => {
    fetchData(endpoint, label);
  };

  // --- Category definitions ---
  const categories = [
    { name: "Movies", endpoint: "listMovies", icon: "https://cdn-icons-png.flaticon.com/512/2809/2809200.png" },
    { name: "Restaurants", endpoint: "listFoods", label: "Food", icon: "https://cdn-icons-png.flaticon.com/512/857/857681.png" },
    { name: "Shops", endpoint: "listShops", icon: "https://cdn-icons-png.flaticon.com/512/2928/2928859.png" },
    { name: "Locations", endpoint: "listLocations", icon: "https://cdn-icons-png.flaticon.com/512/3223/3223126.png" },
    { name: "Events", endpoint: "listEvents", icon: "https://cdn-icons-png.flaticon.com/512/2232/2232658.png" },
    { name: "Activities", endpoint: "listActivities", icon: "https://cdn-icons-png.flaticon.com/512/3048/3048304.png" },
  ];

  // --- Generic Card Renderer (UPDATED) ---
  const renderCard = (item) => {
    const title = item.title || item.name;
    const rating = item.rating ? `⭐ ${item.rating}` : '';
    const image = item.poster || item.image;
    const location = item.location || item.area || item.venue || item.place;
    const details = item.genre || item.category || item.type;
    
    // NEW: Check for price or cost and format it with Rupee symbol
    const price = item.price > 0 ? `₹${item.price}` : (item.cost > 0 ? `₹${item.cost}` : '');

    return (
        <div key={item.id} className="movie-card">
            <h4>{title}</h4>
            <p>{details} {price} {rating}</p>
            {location && <p>📍 {location}</p>}
            {image && (
                <img src={`data:image/jpeg;base64,${image}`} alt={title} />
            )}
        </div>
    );
  };


  return (
    <>
      <header className="app-header">
        <div className="logo">NearMe</div> {/* Changed to NearMe */}
      </header>

      <main>
        <section className="hero">
          <div className="hero-content">
            <h1>Explore What's Near You</h1>
            <p>Enter your location to find the best places to eat, shop, and visit.</p>
            <div className="search-bar">
                 <input 
                    type="text" 
                    value={searchText}
                    onChange={(e) => setSearchText(e.target.value)}
                    placeholder="e.g., Rajamahendravaram"
                 />
                 <button className="btn" onClick={handleSearch}>Search</button>
            </div>
          </div>
        </section>

        <section id="explore" className="container">
          <div className="section-title">
            <h2>What are you looking for in {userLocation}?</h2>
          </div>
          <div className="explore-grid">
            {categories.map(cat => (
              <div key={cat.name} className="category-card" onClick={() => handleCategoryClick(cat.endpoint, cat.label || cat.name)}>
                <img src={cat.icon} alt={cat.name} />
                <h3>{cat.name}</h3>
              </div>
            ))}
          </div>
        </section>

        <section className="container">
          <div className="data-display">
             <h3>{dataType ? `Showing ${dataType}` : "Select a category"}</h3>
             <div className="movie-grid">
                {data.length > 0 
                  ? data.map(renderCard) 
                  : <p>No results found for "{userLocation}". Try a different location or check your spelling.</p>
                }
             </div>
          </div>
        </section>
      </main>

      <footer className="app-footer">
        <p>&copy; {new Date().getFullYear()} NearMe. All Rights Reserved.</p>
      </footer>
    </>
  );
}

export default App;