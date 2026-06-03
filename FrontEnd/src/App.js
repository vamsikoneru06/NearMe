import React, { useState, useEffect, useRef } from "react";
import "./App.css";

const BACKEND = process.env.REACT_APP_BACKEND_URL || "http://localhost:8080";

const CATEGORIES = [
  { name: "Movies",      endpoint: "listMovies",      icon: "🎬" },
  { name: "Restaurants", endpoint: "listRestaurants", icon: "🍽️" },
  { name: "Shops",       endpoint: "listShops",       icon: "🛍️" },
  { name: "Locations",   endpoint: "listLocations",   icon: "📍" },
  { name: "Events",      endpoint: "listEvents",      icon: "🎉" },
  { name: "Activities",  endpoint: "listActivities",  icon: "🏄" },
];

// ─── Main App ────────────────────────────────────────────────────────────────

function App() {
  const [data, setData]           = useState([]);
  const [dataType, setDataType]   = useState("Movies");
  const [userLocation, setUserLocation] = useState("Rajahmundry");
  const [searchText, setSearchText]     = useState("Rajahmundry");
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError]         = useState(null);
  const [isModalOpen, setIsModalOpen]   = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);
  const [theme, setTheme]         = useState("dark");

  // Refs hold the current endpoint & label so the location-change effect
  // always re-fetches with the correct category, avoiding stale closures.
  const endpointRef = useRef("listMovies");
  const labelRef    = useRef("Movies");

  useEffect(() => {
    document.body.className = `${theme}-theme`;
  }, [theme]);

  const fetchData = async (endpoint, label) => {
    const loc = userLocation.trim();
    if (!loc) return;
    endpointRef.current = endpoint;
    labelRef.current    = label;
    setDataType(label);
    setIsLoading(true);
    setError(null);
    try {
      const url = `${BACKEND}/${endpoint}?location=${encodeURIComponent(loc)}`;
      const res = await fetch(url);
      if (!res.ok) throw new Error(`Server error ${res.status}`);
      setData(await res.json());
    } catch {
      setError(`Could not load ${label}. Make sure the backend is running.`);
      setData([]);
    } finally {
      setIsLoading(false);
    }
  };

  // Re-fetch the current category whenever the city changes.
  // fetchData reads userLocation directly (in deps), and uses refs for
  // endpoint/label — so there is no stale-closure issue here.
  useEffect(() => {
    fetchData(endpointRef.current, labelRef.current);
  }, [userLocation]); // intentional: only re-run when location changes

  const handleSearch = () => {
    const loc = searchText.trim();
    if (loc) setUserLocation(loc);
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") handleSearch();
  };

  const openModal  = (item) => { setSelectedItem(item); setIsModalOpen(true); };
  const closeModal = ()     => setIsModalOpen(false);

  const currentIcon = CATEGORIES.find(c => c.name === dataType)?.icon ?? "📌";

  const renderCard = (item) => {
    const title    = item.title || item.name;
    const image    = item.poster || item.image;
    const location = item.location || item.area || item.venue || item.place;
    return (
      <div key={item.id} className="summary-card" onClick={() => openModal(item)}>
        {image
          ? <img
              src={`${BACKEND}${image}`}
              alt={title}
              onError={e => { e.target.style.display = "none"; }}
            />
          : <div className="card-placeholder">{currentIcon}</div>
        }
        <div className="summary-card-content">
          <h4>{title}</h4>
          {location && <p>📍 {location}</p>}
          {item.rating > 0 && <p className="rating">⭐ {item.rating}</p>}
        </div>
      </div>
    );
  };

  const renderContent = () => {
    if (isLoading) return (
      <div className="feedback-container">
        <div className="loader" />
      </div>
    );
    if (error) return (
      <div className="feedback-container error-text">{error}</div>
    );
    if (data.length > 0) return (
      <div className="card-grid">{data.map(renderCard)}</div>
    );
    return (
      <div className="feedback-container">
        <p>No results found for &quot;{userLocation}&quot;. Try another city.</p>
      </div>
    );
  };

  return (
    <>
      <header className="app-header">
        <div className="header-inner">
          <div className="logo">NearMe</div>
          <button className="theme-toggle-btn" onClick={() => setTheme(t => t === "dark" ? "light" : "dark")} aria-label="Toggle theme">
            {theme === "dark" ? "☀️" : "🌙"}
          </button>
        </div>
      </header>

      <main className="container">
        <section className="hero">
          <div className="hero-content">
            <h1>Explore What's Near You</h1>
            <p>Find the best places to eat, shop, and visit in your city.</p>
            <div className="search-bar">
              <input
                type="text"
                value={searchText}
                onChange={e => setSearchText(e.target.value)}
                onKeyDown={handleKeyDown}
                placeholder="e.g., Hyderabad, Mumbai, Chennai…"
              />
              <button className="btn" onClick={handleSearch}>Search</button>
            </div>
          </div>
        </section>

        <section className="category-section">
          <div className="category-grid">
            {CATEGORIES.map(cat => (
              <div
                key={cat.name}
                className={`category-card${dataType === cat.name ? " active" : ""}`}
                onClick={() => fetchData(cat.endpoint, cat.name)}
              >
                <span className="cat-icon">{cat.icon}</span>
                <h3>{cat.name}</h3>
              </div>
            ))}
          </div>
        </section>

        <section className="results-section">
          <h2>{`Showing ${dataType} in ${userLocation}`}</h2>
          {renderContent()}
        </section>
      </main>

      {isModalOpen && selectedItem && (
        <DetailsModal item={selectedItem} dataType={dataType} onClose={closeModal} />
      )}

      <footer className="footer">
        <p>&copy; {new Date().getFullYear()} NearMe. All Rights Reserved.</p>
      </footer>
    </>
  );
}

// ─── Modal ───────────────────────────────────────────────────────────────────

function DetailsModal({ item, dataType, onClose }) {
  const title = item.title || item.name;
  const image = item.poster || item.image;

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={e => e.stopPropagation()}>
        <button className="close-button" onClick={onClose}>&times;</button>

        {image && (
          <img
            className="modal-image"
            src={`${BACKEND}${image}`}
            alt={title}
            onError={e => { e.target.style.display = "none"; }}
          />
        )}

        <h2>{title}</h2>

        {dataType === "Movies"      && <SeatingChart   seating={item.seatingLayout}  />}
        {dataType === "Restaurants" && <MenuDisplay    menu={item.menu}              />}
        {(dataType === "Activities" || dataType === "Events") &&
                                       <TimeSlotDisplay slots={item.availableSlots}  />}
        {dataType === "Shops"       && <PlaceDetail    item={item} dataType={dataType} />}
        {dataType === "Locations"   && <PlaceDetail    item={item} dataType={dataType} />}
      </div>
    </div>
  );
}

// ─── Detail sub-components ───────────────────────────────────────────────────

function SeatingChart({ seating }) {
  if (!seating || seating.length === 0) {
    return <p className="no-data">No seating information available.</p>;
  }
  const rows = seating.reduce((acc, seat) => {
    (acc[seat.seatRow] = acc[seat.seatRow] || []).push(seat);
    return acc;
  }, {});
  const available = seating.filter(s => s.status === "AVAILABLE").length;
  const total     = seating.length;

  return (
    <div className="detail-section">
      <h4>Seating — {available} of {total} seats available</h4>
      <div className="seating-chart">
        <div className="screen">SCREEN</div>
        {Object.keys(rows).sort().map(rowName => (
          <div key={rowName} className="seating-row">
            <span className="row-label">{rowName}</span>
            <div className="seat-row-inner">
              {rows[rowName].map(seat => (
                <div
                  key={`${seat.seatRow}${seat.seatNumber}`}
                  className={`seat ${seat.status.toLowerCase()}`}
                  title={`${seat.seatRow}${seat.seatNumber} — ${seat.status}`}
                >
                  {seat.seatNumber}
                </div>
              ))}
            </div>
          </div>
        ))}
      </div>
      <div className="seating-legend">
        <span className="legend-item"><span className="seat available sample" /> Available</span>
        <span className="legend-item"><span className="seat booked sample"    /> Booked</span>
      </div>
    </div>
  );
}

function MenuDisplay({ menu }) {
  if (!menu || menu.length === 0) {
    return <p className="no-data">Menu not available.</p>;
  }
  return (
    <div className="detail-section">
      <h4>Menu</h4>
      <ul className="menu-list">
        {menu.map((item, i) => (
          <li key={i} className={item.popular ? "popular" : ""}>
            <span>{item.dishName}{item.popular && " 🔥"}</span>
            <span className="price">₹{item.dishPrice}</span>
          </li>
        ))}
      </ul>
    </div>
  );
}

function TimeSlotDisplay({ slots }) {
  if (!slots || slots.length === 0) {
    return <p className="no-data">No time slots available.</p>;
  }
  return (
    <div className="detail-section">
      <h4>Available Slots</h4>
      <div className="slot-list">
        {slots.map((slot, i) => (
          <div key={i} className="slot">
            <span className="time">{slot.time}</span>
            <span className="spots">{slot.availableSpots} spots left</span>
          </div>
        ))}
      </div>
    </div>
  );
}

function PlaceDetail({ item, dataType }) {
  const rows = dataType === "Shops"
    ? [
        { label: "Category", value: item.category },
        { label: "Location", value: item.location },
        { label: "Rating",   value: item.rating ? `⭐ ${item.rating}` : null },
      ]
    : [
        { label: "Area",   value: item.area },
        { label: "Rating", value: item.rating ? `⭐ ${item.rating}` : null },
      ];

  return (
    <div className="detail-section">
      <h4>Details</h4>
      <ul className="detail-list">
        {rows.filter(r => r.value).map(({ label, value }) => (
          <li key={label}>
            <span className="detail-label">{label}</span>
            <span>{value}</span>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
