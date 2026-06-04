import React, { useState, useEffect, useRef, useCallback } from "react";
import "./App.css";

const BACKEND = process.env.REACT_APP_BACKEND_URL || "http://localhost:8080";
const MAP_KEY  = process.env.REACT_APP_MAP_API_KEY  || "";

const CATEGORIES = [
  { name: "Movies",      endpoint: "api/movies",       icon: "🎬", color: "#ef4444" },
  { name: "Restaurants", endpoint: "api/restaurants",  icon: "🍽️", color: "#f97316" },
  { name: "Shops",       endpoint: "api/shops",        icon: "🛍️", color: "#a855f7" },
  { name: "Locations",   endpoint: "api/locations",    icon: "📍", color: "#22c55e" },
  { name: "Events",      endpoint: "api/events",       icon: "🎉", color: "#ec4899" },
  { name: "Activities",  endpoint: "api/activities",   icon: "🏄", color: "#14b8a6" },
];

// Maps each category to the Google Places search query
const PLACES_QUERY = {
  Movies:      "cinema movie theater",
  Restaurants: "restaurant",
  Shops:       "shopping mall market",
  Locations:   "tourist attraction landmark",
  Events:      "event venue stadium",
  Activities:  "amusement park activity sport",
};

// imageUrl  = unified Location API (full https:// URL or /images/ path)
// image     = old category entities (relative path like /images/file.jpg)
// poster    = Movie entities (relative path)
const imgSrc = (item) => {
  const path = item.poster || item.imageUrl || item.image;
  if (!path) return "";
  // Full URLs (Wikimedia, Unsplash, etc.) — use directly
  if (path.startsWith("http")) return path;
  // Relative paths — prefix with backend host
  return `${BACKEND}${path}`;
};

// Seed string → consistent Picsum photo (400×280)
const picsumUrl = (seed) =>
  `https://picsum.photos/seed/${encodeURIComponent(seed)}/400/280`;

function handleImgError(e, fallbackStyle) {
  const src = e.target.src;

  // External URLs (Google Places photos, Wikimedia, Picsum) that fail → go straight to Picsum
  const isExternal = src.startsWith("https://maps.googleapis.com") ||
                     src.startsWith("https://upload.wikimedia.org") ||
                     src.startsWith("https://picsum.photos");

  if (isExternal || e.target.dataset.tried === "local") {
    e.target.dataset.tried = "picsum";
    const seed = src.split("/").pop().split("?")[0].replace(/\.[^.]+$/, "") || "nearme";
    e.target.src = picsumUrl(seed);
    return;
  }

  if (!e.target.dataset.tried) {
    // First failure on a relative path — try the local public/images copy
    e.target.dataset.tried = "local";
    const filename = src.split("/").pop().split("?")[0];
    e.target.src = `/images/${filename}`;
    return;
  }

  // All fallbacks exhausted — hide and apply gradient placeholder
  e.target.style.display = "none";
  if (fallbackStyle && e.target.parentNode)
    Object.assign(e.target.parentNode.style, fallbackStyle);
}

/* ── SVG Icons ──────────────────────────────────────────────────────────── */
const IcoMovies     = () => <svg width="17" height="17" viewBox="0 0 20 20" fill="none"><rect x="2" y="4" width="16" height="12" rx="2" stroke="currentColor" strokeWidth="1.6"/><line x1="6" y1="4" x2="6" y2="16" stroke="currentColor" strokeWidth="1.4"/><line x1="14" y1="4" x2="14" y2="16" stroke="currentColor" strokeWidth="1.4"/><line x1="2" y1="10" x2="18" y2="10" stroke="currentColor" strokeWidth="1.4"/></svg>;
const IcoRestaurants= () => <svg width="17" height="17" viewBox="0 0 20 20" fill="none"><path d="M6 3v6c0 1.66 1.34 3 3 3s3-1.34 3-3V3" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round"/><line x1="9" y1="12" x2="9" y2="17" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round"/><line x1="6" y1="17" x2="12" y2="17" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round"/></svg>;
const IcoShops      = () => <svg width="17" height="17" viewBox="0 0 20 20" fill="none"><path d="M4 6h12l-1.5 9a1 1 0 01-1 .9H6.5a1 1 0 01-1-.9L4 6z" stroke="currentColor" strokeWidth="1.6"/><path d="M7.5 6V5a2.5 2.5 0 015 0v1" stroke="currentColor" strokeWidth="1.6"/></svg>;
const IcoLocations  = () => <svg width="17" height="17" viewBox="0 0 20 20" fill="none"><circle cx="10" cy="8" r="3" stroke="currentColor" strokeWidth="1.6"/><path d="M10 15s-6-4.5-6-7a6 6 0 0112 0c0 2.5-6 7-6 7z" stroke="currentColor" strokeWidth="1.6"/></svg>;
const IcoEvents     = () => <svg width="17" height="17" viewBox="0 0 20 20" fill="none"><rect x="3" y="5" width="14" height="12" rx="2" stroke="currentColor" strokeWidth="1.6"/><line x1="3" y1="9" x2="17" y2="9" stroke="currentColor" strokeWidth="1.4"/><line x1="7" y1="3" x2="7" y2="6" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round"/><line x1="13" y1="3" x2="13" y2="6" stroke="currentColor" strokeWidth="1.6" strokeLinecap="round"/></svg>;
const IcoActivities = () => <svg width="17" height="17" viewBox="0 0 20 20" fill="none"><path d="M10 3l2 5h5l-4 3 1.5 5L10 13l-4.5 3L7 11 3 8h5l2-5z" stroke="currentColor" strokeWidth="1.4" strokeLinejoin="round"/></svg>;
const IcoSearch     = () => <svg width="17" height="17" viewBox="0 0 20 20" fill="none"><circle cx="9" cy="9" r="5.5" stroke="currentColor" strokeWidth="1.8"/><line x1="13.2" y1="13.2" x2="17" y2="17" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round"/></svg>;
const IcoPin        = () => <svg width="13" height="13" viewBox="0 0 16 16" fill="none"><circle cx="8" cy="6.5" r="2.5" stroke="currentColor" strokeWidth="1.5"/><path d="M8 10.5C8 10.5 3 13 3 6.5a5 5 0 0110 0C13 13 8 10.5 8 10.5z" stroke="currentColor" strokeWidth="1.5"/></svg>;
const IcoMoon       = () => <svg width="14" height="14" viewBox="0 0 24 24" fill="none"><path d="M21 12.79A9 9 0 1111.21 3a7 7 0 109.79 9.79z" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/></svg>;
const IcoSun        = () => <svg width="14" height="14" viewBox="0 0 24 24" fill="none"><circle cx="12" cy="12" r="5" stroke="currentColor" strokeWidth="2"/><line x1="12" y1="1" x2="12" y2="3" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/><line x1="12" y1="21" x2="12" y2="23" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/><line x1="4.22" y1="4.22" x2="5.64" y2="5.64" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/><line x1="18.36" y1="18.36" x2="19.78" y2="19.78" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/><line x1="1" y1="12" x2="3" y2="12" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/><line x1="21" y1="12" x2="23" y2="12" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/><line x1="4.22" y1="19.78" x2="5.64" y2="18.36" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/><line x1="18.36" y1="5.64" x2="19.78" y2="4.22" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/></svg>;
const IcoStar       = () => <svg width="11" height="11" viewBox="0 0 12 12" fill="#f59e0b"><path d="M6 1l1.5 3 3.5.5-2.5 2.3.6 3.2L6 8.5l-3.1 1.5.6-3.2L1 4.5l3.5-.5L6 1z"/></svg>;
const IcoLocPin     = () => <svg width="14" height="14" viewBox="0 0 16 16" fill="none"><circle cx="8" cy="6.5" r="2.5" stroke="white" strokeWidth="1.4"/><path d="M8 10.5C8 10.5 3 13 3 6.5a5 5 0 0110 0C13 13 8 10.5 8 10.5z" stroke="white" strokeWidth="1.4"/></svg>;
const IcoPinSm      = () => <svg width="10" height="10" viewBox="0 0 12 12" fill="none"><circle cx="6" cy="5" r="2" stroke="rgba(255,255,255,.50)" strokeWidth="1.2"/><path d="M6 8C6 8 2 5.5 2 5a4 4 0 018 0C10 5.5 6 8 6 8z" stroke="rgba(255,255,255,.50)" strokeWidth="1.2"/></svg>;
const IcoCal        = () => <svg width="13" height="13" viewBox="0 0 14 14" fill="none"><rect x="1" y="2" width="12" height="11" rx="1.5" stroke="rgba(255,255,255,.75)" strokeWidth="1.3"/><line x1="1" y1="5.5" x2="13" y2="5.5" stroke="rgba(255,255,255,.75)" strokeWidth="1.3"/><line x1="4" y1="0.5" x2="4" y2="3.5" stroke="rgba(255,255,255,.75)" strokeWidth="1.3" strokeLinecap="round"/><line x1="10" y1="0.5" x2="10" y2="3.5" stroke="rgba(255,255,255,.75)" strokeWidth="1.3" strokeLinecap="round"/></svg>;

const CAT_ICONS = { Movies: IcoMovies, Restaurants: IcoRestaurants, Shops: IcoShops, Locations: IcoLocations, Events: IcoEvents, Activities: IcoActivities };

/* ── Free OSM Map — Nominatim geocoding + OpenStreetMap embed ────────────── */
const _geoCache = {}; // module-level: survives re-renders, cleared on page refresh

function MapView({ query, lat, lng }) {
  const hasCoords = lat != null && lng != null;

  const [coords,  setCoords]  = useState(hasCoords ? { lat, lng, _query: query } : (_geoCache[query] ?? null));
  const [status,  setStatus]  = useState(
    hasCoords || _geoCache[query] ? "ok" : "loading"
  );

  useEffect(() => {
    if (hasCoords) { setCoords({ lat, lng, _query: query }); setStatus("ok"); return; }
    if (!query)    { setStatus("empty"); return; }
    // With Google Maps API key, skip geocoding — the embed API resolves text queries itself
    if (MAP_KEY)   { setCoords({ lat: 0, lng: 0, _query: query }); setStatus("ok"); return; }
    if (_geoCache[query]) { setCoords(_geoCache[query]); setStatus("ok"); return; }

    setStatus("loading");
    fetch(
      `https://nominatim.openstreetmap.org/search?q=${encodeURIComponent(query)}&format=json&limit=1`,
      { headers: { "Accept-Language": "en" } }
    )
      .then(r => r.json())
      .then(data => {
        if (data && data[0]) {
          const c = { lat: parseFloat(data[0].lat), lng: parseFloat(data[0].lon), _query: query };
          _geoCache[query] = c;
          setCoords(c);
          setStatus("ok");
        } else {
          setStatus("notfound");
        }
      })
      .catch(() => setStatus("error"));
  }, [query, lat, lng, hasCoords]);

  if (status === "loading") return (
    <div style={{ height: 260, display: "grid", placeItems: "center",
                  background: "var(--card-2)", borderRadius: 14,
                  color: "var(--fg2)", fontSize: "0.85rem", fontFamily: "'Inter',sans-serif",
                  gap: 10, flexDirection: "column" }}>
      <div style={{ width: 28, height: 28, border: "3px solid var(--border)",
                    borderTopColor: "var(--accent)", borderRadius: "50%",
                    animation: "spin 0.8s linear infinite" }} />
      Locating on map…
    </div>
  );

  if (status !== "ok" || !coords) return (
    <div style={{ height: 260, display: "grid", placeItems: "center",
                  background: "var(--card-2)", borderRadius: 14,
                  color: "var(--fg2)", fontSize: "0.85rem", fontFamily: "'Inter',sans-serif" }}>
      📍 Map unavailable — use the link below
    </div>
  );

  const encodedQuery = encodeURIComponent(coords._query || query || "");
  const src = MAP_KEY
    ? `https://www.google.com/maps/embed/v1/place?key=${MAP_KEY}&q=${encodedQuery}&zoom=15`
    : (() => {
        const d = 0.013;
        const bbox = `${coords.lng - d},${coords.lat - d},${coords.lng + d},${coords.lat + d}`;
        return `https://www.openstreetmap.org/export/embed.html?bbox=${bbox}&layer=mapnik&marker=${coords.lat},${coords.lng}`;
      })();

  return (
    <iframe title="map" src={src} width="100%" height="260"
      style={{ border: 0, display: "block", borderRadius: 14 }}
      loading="lazy" referrerPolicy="no-referrer-when-downgrade" allowFullScreen />
  );
}

/* ── 3D Tilt Hook ────────────────────────────────────────────────────────── */
function use3DTilt(strength = 8) {
  const elRef = useRef(null);
  const st = useRef({ ox: 0, oy: 0, tx: 0, ty: 0, raf: null });

  const onMouseMove = useCallback((e) => {
    const el = elRef.current; if (!el) return;
    const r = el.getBoundingClientRect();
    const s = st.current;
    s.tx = (e.clientX - r.left) / r.width - 0.5;
    s.ty = (e.clientY - r.top) / r.height - 0.5;
    if (!s.raf) s.raf = requestAnimationFrame(function tick() {
      s.raf = null;
      s.ox += (s.tx - s.ox) * 0.14;
      s.oy += (s.ty - s.oy) * 0.14;
      if (elRef.current)
        elRef.current.style.transform = `perspective(700px) rotateX(${-s.oy * strength}deg) rotateY(${s.ox * strength}deg) translateY(-5px) scale(1.012)`;
      if (Math.abs(s.tx - s.ox) > 0.002 || Math.abs(s.ty - s.oy) > 0.002)
        s.raf = requestAnimationFrame(tick);
    });
  }, [strength]);

  const onMouseLeave = useCallback(() => {
    const s = st.current;
    s.tx = 0; s.ty = 0;
    (function reset() {
      s.ox += (0 - s.ox) * 0.12; s.oy += (0 - s.oy) * 0.12;
      if (elRef.current)
        elRef.current.style.transform = `perspective(700px) rotateX(${-s.oy * strength}deg) rotateY(${s.ox * strength}deg)`;
      if (Math.abs(s.ox) > 0.001 || Math.abs(s.oy) > 0.001) requestAnimationFrame(reset);
      else if (elRef.current) { elRef.current.style.transform = ""; s.ox = 0; s.oy = 0; }
    })();
  }, [strength]);

  return { ref: elRef, onMouseMove, onMouseLeave };
}

/* ── Scroll Reveal Hook ──────────────────────────────────────────────────── */
function useReveal(delay = 0) {
  const ref = useRef(null);
  useEffect(() => {
    const el = ref.current; if (!el) return;
    el.style.transitionDelay = `${delay}s`;
    const obs = new IntersectionObserver(([entry]) => {
      if (entry.isIntersecting) { el.classList.add("revealed"); obs.disconnect(); }
    }, { threshold: 0.10 });
    obs.observe(el);
    return () => obs.disconnect();
  }, [delay]);
  return ref;
}

/* ── Theme Toggle ────────────────────────────────────────────────────────── */
function ThemeToggle({ isDark, onToggle }) {
  return (
    <button className={`theme-toggle ${isDark ? "is-dark" : "is-light"}`} onClick={onToggle} aria-label="Toggle theme">
      <div className="toggle-thumb">{isDark ? <IcoMoon /> : <IcoSun />}</div>
    </button>
  );
}

/* ── Expandable Tabs ─────────────────────────────────────────────────────── */
function ExpandableTabs({ active, onPick }) {
  return (
    <nav className="exp-nav">
      {CATEGORIES.map((cat, i) => {
        const Icon = CAT_ICONS[cat.name];
        return (
          <React.Fragment key={cat.name}>
            {i === 3 && <div className="nav-sep" />}
            <button
              className={`exp-tab${cat.name === active ? " active" : ""}`}
              onClick={() => onPick(cat.name)}
            >
              <span className="tab-icon">{Icon && <Icon />}</span>
              <span className="tab-label">{cat.name}</span>
            </button>
          </React.Fragment>
        );
      })}
    </nav>
  );
}

/* ── Header ──────────────────────────────────────────────────────────────── */
function Header({ theme, onToggle, active, onPick }) {
  const [scrolled, setScrolled] = useState(false);
  useEffect(() => {
    const fn = () => setScrolled(window.scrollY > 24);
    window.addEventListener("scroll", fn, { passive: true });
    return () => window.removeEventListener("scroll", fn);
  }, []);

  return (
    <header className={`hdr${scrolled ? " scrolled" : ""}`}>
      <div className="hdr-inner">
        {/* ── Left: Wordmark logo ───────────────────────────────────────── */}
        <a href="#" className="wm" onClick={e => e.preventDefault()}>
          <svg className="wm-pin" viewBox="0 0 56 70" fill="none">
            <defs>
              <linearGradient id="hpg" x1="0" y1="0" x2="56" y2="70" gradientUnits="userSpaceOnUse">
                <stop offset="0%" stopColor="#1a0a2e"/>
                <stop offset="50%" stopColor="#c84b31"/>
                <stop offset="100%" stopColor="#f4a228"/>
              </linearGradient>
            </defs>
            <path d="M28 2C14.745 2 4 12.745 4 26C4 42 28 68 28 68C28 68 52 42 52 26C52 12.745 41.255 2 28 2Z" fill="url(#hpg)"/>
            <circle cx="28" cy="26" r="10" fill="rgba(250,247,242,0.90)"/>
            <circle cx="28" cy="26" r="3.5" fill="url(#hpg)" opacity="0.65"/>
          </svg>
          <span className="wm-text">NearMe</span>
        </a>

        {/* ── Centre: Category nav ─────────────────────────────────────── */}
        <ExpandableTabs active={active} onPick={onPick} />

        {/* ── Right: Theme toggle ──────────────────────────────────────── */}
        <div className="hdr-right">
          <ThemeToggle isDark={theme === "dark"} onToggle={onToggle} />
        </div>
      </div>
    </header>
  );
}

/* ── Bubble canvas helpers ───────────────────────────────────────────────── */
const BUBBLE_HUES = [10, 25, 280, 200, 330, 170, 35, 260, 45, 180, 300, 150];

function mkBubble(W, H, fromBottom) {
  const r = 10 + Math.random() * 32;
  return {
    x: r + Math.random() * (W - r * 2),
    y: fromBottom ? H + r + Math.random() * 80 : Math.random() * H,
    r, vy: -(0.15 + Math.random() * 0.26),
    vx: (Math.random() - 0.5) * 0.12,
    hue: BUBBLE_HUES[Math.floor(Math.random() * BUBBLE_HUES.length)],
    alpha: 0.50 + Math.random() * 0.38,
    phase: Math.random() * Math.PI * 2,
    pop: 0,
  };
}

/* ── Hero ────────────────────────────────────────────────────────────────── */
function Hero({ city, value, onChange, onSearch }) {
  const canvasRef = useRef(null);
  const mouseRef  = useRef({ x: -999, y: -999 });

  useEffect(() => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const ctx = canvas.getContext("2d");
    let animId;

    function resize() {
      canvas.width  = canvas.offsetWidth;
      canvas.height = canvas.offsetHeight;
    }
    resize();
    const ro = new ResizeObserver(resize);
    ro.observe(canvas);

    let bubbles = Array.from({ length: 28 }, () => mkBubble(canvas.width, canvas.height, false));

    function drawBubble(b, t) {
      if (b.pop > 0) {
        const rr = b.r * (1 + b.pop * 2.6);
        ctx.beginPath(); ctx.arc(b.x, b.y, rr, 0, Math.PI * 2);
        ctx.strokeStyle = `hsla(${b.hue},60%,65%,${Math.max(0, 0.5 - b.pop * 0.6)})`;
        ctx.lineWidth = 1.8; ctx.stroke();
        ctx.beginPath(); ctx.arc(b.x, b.y, rr * 0.55, 0, Math.PI * 2);
        ctx.strokeStyle = `hsla(${b.hue},50%,72%,${Math.max(0, 0.28 - b.pop * 0.35)})`;
        ctx.lineWidth = 1; ctx.stroke();
        return;
      }
      const W = b.r, wobble = Math.sin(t * 0.9 + b.phase) * 1.4;
      const g = ctx.createRadialGradient(b.x - W * 0.3, b.y - W * 0.3, W * 0.05, b.x, b.y, W + wobble);
      g.addColorStop(0, `hsla(${b.hue},75%,90%,${b.alpha * 0.55})`);
      g.addColorStop(0.5, `hsla(${b.hue},60%,75%,${b.alpha * 0.22})`);
      g.addColorStop(1, `hsla(${b.hue},55%,62%,${b.alpha * 0.16})`);
      ctx.beginPath(); ctx.arc(b.x, b.y + wobble, W, 0, Math.PI * 2);
      ctx.fillStyle = g; ctx.fill();
      ctx.strokeStyle = `hsla(${b.hue},55%,78%,${b.alpha * 0.50})`; ctx.lineWidth = 1; ctx.stroke();
      ctx.beginPath();
      ctx.ellipse(b.x - W * 0.28, b.y - W * 0.28 + wobble, W * 0.22, W * 0.12, -0.6, 0, Math.PI * 2);
      ctx.fillStyle = "rgba(255,255,255,0.72)"; ctx.fill();
    }

    let lastTime = 0;
    function draw(now) {
      animId = requestAnimationFrame(draw);
      const t = now / 1000, dt = Math.min((now - lastTime) / 16, 3); lastTime = now;
      const { x: mx, y: my } = mouseRef.current;
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      bubbles.forEach((b, i) => {
        if (b.pop > 0) { b.pop += 0.05 * dt; if (b.pop > 1) bubbles[i] = mkBubble(canvas.width, canvas.height, true); return; }
        const dx = mx - b.x, dy = my - b.y;
        if (Math.sqrt(dx * dx + dy * dy) < b.r + 22) { b.pop = 0.001; return; }
        b.y += b.vy * dt; b.x += b.vx * dt;
        if (b.y < -b.r * 2) bubbles[i] = mkBubble(canvas.width, canvas.height, true);
        drawBubble(b, t);
      });
      bubbles.forEach(b => { if (b.pop > 0) drawBubble(b, t); });
    }
    animId = requestAnimationFrame(draw);
    return () => { cancelAnimationFrame(animId); ro.disconnect(); };
  }, []);

  const onMove  = useCallback((e) => {
    const r = canvasRef.current?.getBoundingClientRect();
    if (r) mouseRef.current = { x: e.clientX - r.left, y: e.clientY - r.top };
  }, []);
  const onLeave = useCallback(() => { mouseRef.current = { x: -999, y: -999 }; }, []);

  return (
    <section className="hero" onMouseMove={onMove} onMouseLeave={onLeave}>
      <canvas ref={canvasRef} className="hero-canvas" />
      <div className="hero-inner">
        <div className="loc-chip"><IcoPin /> {city}</div>
        <div className="hero-title-wrap">
          <h1 className="hero-h1">Explore What's Near You</h1>
          <svg viewBox="0 0 600 20" height="14" fill="none" style={{ display: "block", width: "100%", overflow: "visible", marginTop: "-2px" }}>
            <path d="M8 13 C100 5,260 17,420 10 C490 5,555 15,596 11" stroke="url(#hsg)" strokeWidth="2.2" strokeLinecap="round" opacity="0.65"/>
            <path d="M582 11 Q596 7 602 15" stroke="url(#hsg)" strokeWidth="1.6" strokeLinecap="round" opacity="0.45"/>
            <defs>
              <linearGradient id="hsg" x1="0" y1="0" x2="600" y2="0" gradientUnits="userSpaceOnUse">
                <stop offset="0%" stopColor="#1a0a2e"/><stop offset="50%" stopColor="#c84b31"/><stop offset="100%" stopColor="#f4a228"/>
              </linearGradient>
            </defs>
          </svg>
        </div>
        <p className="hero-sub">Discover the best movies, restaurants, shops &amp; experiences around you.</p>
        <div className="search-outer">
          <div className="search-bar">
            <span className="srch-icon"><IcoSearch /></span>
            <input
              className="srch-input"
              value={value}
              onChange={e => onChange(e.target.value)}
              onKeyDown={e => e.key === "Enter" && onSearch()}
              placeholder="Search movies, places, restaurants…"
            />
            <div className="srch-divider" />
            <button className="srch-btn" onClick={onSearch}>Search</button>
          </div>
        </div>
      </div>
    </section>
  );
}

/* ── Category pills ──────────────────────────────────────────────────────── */
function CategoryPills({ active, onPick }) {
  return (
    <div className="cat-pills">
      {CATEGORIES.map(cat => {
        const Icon = CAT_ICONS[cat.name];
        const on = cat.name === active;
        return (
          <button
            key={cat.name}
            className={`cat-pill${on ? " active" : ""}`}
            style={on ? { background: `${cat.color}18`, borderColor: `${cat.color}40`, color: cat.color, boxShadow: `0 4px 16px -4px ${cat.color}30` } : {}}
            onClick={() => onPick(cat.name)}
          >
            <span className="tab-icon">{Icon && <Icon />}</span>
            {cat.name}
          </button>
        );
      })}
    </div>
  );
}

/* ── Summary Card ────────────────────────────────────────────────────────── */
function SummaryCard({ item, cat, index, onOpen }) {
  const tilt   = use3DTilt(7);
  const reveal = useReveal(index * 0.07);

  const ref = useCallback(el => {
    tilt.ref.current = el;
    reveal.current   = el;
  }, []); // tilt.ref and reveal are stable refs

  const title = item.title || item.name;
  const sub   = item.location || item.venue || item.place || item.area || "";
  const tag   = item.genre || item.type || item.category;
  const h     = cat?.name === "Movies" ? 300 : 260;
  const src   = imgSrc(item);

  return (
    <div ref={ref} className="sc reveal"
      onMouseMove={tilt.onMouseMove}
      onMouseLeave={tilt.onMouseLeave}
      onClick={() => onOpen(item)}
    >
      <div className="media" style={{ height: h }}>
        {item.rating > 0 && (
          <div className="rate"><IcoStar /> {item.rating}</div>
        )}
        {tag && (
          <div className="genre" style={{ background: cat?.color ? `${cat.color}cc` : "rgba(0,0,0,0.55)" }}>{tag}</div>
        )}
        <div className="scrim" />
        {src && (
          <img src={src} alt={title} loading="lazy" style={{ height: h }}
            onError={e => handleImgError(e, { background: `linear-gradient(135deg,#1a0a2e,${cat?.color || "#c84b31"})` })}
          />
        )}
      </div>

      <div className="sc-info">
        <div>
          <div className="sc-cat">{cat?.name}{sub ? " · " + sub.split(",")[1]?.trim() : ""}</div>
          <h4 className="sc-title">{title}</h4>
          {sub && (
            <div className="sc-loc"><IcoPinSm /><p>{sub}</p></div>
          )}
        </div>
        <button className="sc-cta" onClick={e => { e.stopPropagation(); onOpen(item); }}>
          <IcoCal /> View Details
        </button>
      </div>
    </div>
  );
}

/* ── Location Card ───────────────────────────────────────────────────────── */
function LocationCard({ item, index, onOpen }) {
  const reveal = useReveal(index * 0.08);
  const src = imgSrc(item);

  // Normalise old (area/image/distance) and new (address/imageUrl/distanceKm) field names
  const areaLabel = item.area || (item.address ? item.address.split(",")[0] : "");
  const distKm    = item.distanceKm != null
    ? item.distanceKm.toFixed(1)
    : item.distance || null;
  const categoryLabel = (item.category || item.type)
    ? (item.category || item.type).replace(/_/g, " ")
    : null;

  return (
    <div ref={reveal} className="loc-card reveal" onClick={() => onOpen(item)}>
      <div className="pic">
        <div className="lpin"><IcoLocPin /></div>
        {distKm && (
          <div className="ldist">
            <span className="km">{distKm}</span>
            <span className="unit">km</span>
          </div>
        )}
        {src && (
          <img src={src} alt={item.name} loading="lazy"
            onError={e => handleImgError(e, { background: "linear-gradient(135deg,#064e3b,#22c55e)" })}
          />
        )}
      </div>
      <div className="linfo">
        {/* Badge: category (new API) or area (old API) */}
        {(categoryLabel || areaLabel) && (
          <span className="larea">{categoryLabel || areaLabel}</span>
        )}
        <h4>{item.name}</h4>
        {/* Address / area subtitle */}
        {areaLabel && !categoryLabel && (
          <p className="larea-sub">{areaLabel}</p>
        )}
        {item.description && (
          <p className="ldesc">{item.description.slice(0, 90)}{item.description.length > 90 ? "…" : ""}</p>
        )}
        {item.rating > 0 && (
          <div className="lrating">
            <svg width="11" height="11" viewBox="0 0 12 12" fill="#f59e0b"><path d="M6 1l1.5 3 3.5.5-2.5 2.3.6 3.2L6 8.5l-3.1 1.5.6-3.2L1 4.5l3.5-.5L6 1z"/></svg>
            <p>{item.rating}{item.city ? " · " + item.city : ""}</p>
          </div>
        )}
        {item.tags && (
          <div className="ltags">{item.tags.map(t => <span key={t} className="ltag">{t}</span>)}</div>
        )}
      </div>
    </div>
  );
}

/* ── Seating Chart ───────────────────────────────────────────────────────── */
function SeatingChart({ seating }) {
  if (!seating || seating.length === 0)
    return <p style={{ color: "var(--fg2)" }}>No seating information available.</p>;

  const rows = {};
  seating.forEach(s => {
    const r = s.seatRow || s.row;
    const n = s.seatNumber ?? s.num;
    (rows[r] = rows[r] || []).push({ ...s, _row: r, _num: n });
  });
  const avail = seating.filter(s => s.status === "AVAILABLE").length;

  return (
    <div>
      <h4 style={{ fontFamily: "'Poppins',sans-serif", fontWeight: 600, marginBottom: 14, color: "var(--fg1)" }}>
        Seating — {avail} of {seating.length} available
      </h4>
      <div className="screen">SCREEN</div>
      {Object.keys(rows).sort().map(r => (
        <div className="srow" key={r}>
          <span className="rl">{r}</span>
          <div className="seats">
            {rows[r].map(s => (
              <div key={s._num} className={`seat ${s.status === "AVAILABLE" ? "av" : "bk"}`}
                title={`${s._row}${s._num} — ${s.status}`}>
                {s._num}
              </div>
            ))}
          </div>
        </div>
      ))}
      <div className="legend">
        <span><span className="lsw" style={{ background: "#064e3b", border: "1px solid #059669" }} />Available</span>
        <span><span className="lsw" style={{ background: "#450a0a", border: "1px solid #dc2626" }} />Booked</span>
      </div>
    </div>
  );
}

/* ── Menu ────────────────────────────────────────────────────────────────── */
function MenuDisplay({ menu }) {
  if (!menu || menu.length === 0)
    return <p style={{ color: "var(--fg2)" }}>Menu not available.</p>;

  return (
    <ul className="menu-list">
      {menu.map((m, i) => {
        const name    = m.dishName || m.d || m.name || "";
        const price   = m.dishPrice ?? m.p ?? m.price ?? 0;
        const popular = m.popular ?? m.pop ?? false;
        return (
          <li key={i} className={popular ? "pop" : ""}>
            <span>{name}{popular && " 🔥"}</span>
            <span className="price">₹{price}</span>
          </li>
        );
      })}
    </ul>
  );
}

/* ── Time Slots ──────────────────────────────────────────────────────────── */
function TimeSlots({ slots }) {
  if (!slots || slots.length === 0)
    return <p style={{ color: "var(--fg2)" }}>No time slots available.</p>;

  return (
    <div className="slots">
      {slots.map((s, i) => {
        const spots = s.availableSpots ?? s.spots ?? 0;
        const cap   = s.capacity ?? s.cap ?? 20;
        const pct   = Math.min(100, Math.round((spots / cap) * 100));
        return (
          <div className="slot" key={i}>
            <div className="t">{s.time}</div>
            <div className="s">{spots} spots left</div>
            <div className="pbar"><i className={pct < 25 ? "low" : ""} style={{ width: pct + "%" }} /></div>
          </div>
        );
      })}
    </div>
  );
}

/* ── Place Detail ────────────────────────────────────────────────────────── */
function PlaceDetail({ item }) {
  const rows = [
    ["Category", item.category],
    ["Type",     item.type],
    ["Area",     item.area],
    ["Location", item.location || item.venue || item.place],
    ["City",     item.city],
    ["Entry",    item.price != null ? (item.price === 0 ? "Free" : "₹" + item.price) : null],
    ["Cost",     item.cost  != null ? "₹" + item.cost : null],
    ["Rating",   item.rating ? "⭐ " + item.rating : null],
  ].filter(r => r[1] != null && r[1] !== "");

  return (
    <ul className="drows">
      {rows.map(([k, v]) => (
        <li key={k}><span className="k">{k}</span><span>{v}</span></li>
      ))}
    </ul>
  );
}

/* ── Detail Modal ────────────────────────────────────────────────────────── */
function DetailModal({ item, cat, onClose }) {
  const tabsFor = {
    Movies:      ["Seats",  "Details", "Map"],
    Restaurants: ["Menu",   "Details", "Map"],
    Activities:  ["Slots",  "Details", "Map"],
    Events:      ["Slots",  "Details", "Map"],
    Shops:       ["Details", "Map"],
    Locations:   ["Details", "Map"],
  }[cat.name] || ["Details", "Map"];

  const [activeTab, setActiveTab] = useState(tabsFor[0]);
  const title = item.title || item.name;
  const src   = imgSrc(item);

  const seating = item.seatingLayout || item.seating;
  const menu    = item.menu;
  const slots   = item.availableSlots || item.slots;

  const panel = () => {
    if (activeTab === "Seats")   return <SeatingChart seating={seating} />;
    if (activeTab === "Menu")    return <MenuDisplay  menu={menu} />;
    if (activeTab === "Slots")   return <TimeSlots    slots={slots} />;
    if (activeTab === "Map") {
      const locStr  = item.location || item.venue || item.place || item.area || item.address || item.name || "";
      const gmLink  = `https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(locStr)}`;
      const osmLink = `https://www.openstreetmap.org/search?query=${encodeURIComponent(locStr)}`;

      return (
        <div>
          <div style={{ borderRadius: 14, overflow: "hidden", marginBottom: 14,
                        border: "1px solid var(--border-soft)" }}>
            <MapView query={locStr} lat={item.latitude} lng={item.longitude} />
          </div>
          <div style={{ display: "flex", alignItems: "center", gap: 10, flexWrap: "wrap" }}>
            <a href={gmLink} target="_blank" rel="noopener noreferrer"
              style={{ display: "inline-flex", alignItems: "center", gap: 6,
                       padding: "9px 20px", background: "#4285F4", color: "#fff",
                       borderRadius: 50, textDecoration: "none",
                       fontSize: "0.84rem", fontFamily: "'Poppins',sans-serif", fontWeight: 600,
                       boxShadow: "0 4px 14px rgba(66,133,244,.35)" }}>
              📍 Open in Google Maps
            </a>
            <a href={osmLink} target="_blank" rel="noopener noreferrer"
              style={{ display: "inline-flex", alignItems: "center", gap: 6,
                       padding: "9px 20px", background: "var(--card-2)", color: "var(--fg1)",
                       border: "1px solid var(--border)", borderRadius: 50, textDecoration: "none",
                       fontSize: "0.84rem", fontFamily: "'Poppins',sans-serif", fontWeight: 600 }}>
              🗺️ OpenStreetMap
            </a>
          </div>
        </div>
      );
    }
    return <PlaceDetail item={item} />;
  };

  return (
    <div className="overlay" onClick={onClose}>
      <div className="modal" onClick={e => e.stopPropagation()}>
        <button className="m-close" onClick={onClose} aria-label="Close">✕</button>
        <div className="mhero">
          {src && <img src={src} alt={title} onError={e => handleImgError(e, null)} />}
          <h2 className="mtitle">{title}</h2>
        </div>
        <div className="mbody">
          <div className="tabs">
            {tabsFor.map(t => (
              <button key={t} className={`tab${t === activeTab ? " active" : ""}`} onClick={() => setActiveTab(t)}>{t}</button>
            ))}
          </div>
          {panel()}
        </div>
      </div>
    </div>
  );
}

/* ── Developer Card ──────────────────────────────────────────────────────── */
const IcoGithub = () => (
  <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor">
    <path d="M12 0C5.374 0 0 5.373 0 12c0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23A11.509 11.509 0 0 1 12 5.803c1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v3.293c0 .319.192.694.801.576C20.566 21.797 24 17.3 24 12c0-6.627-5.373-12-12-12z"/>
  </svg>
);

const IcoLinkedin = () => (
  <svg width="18" height="18" viewBox="0 0 24 24" fill="currentColor">
    <path d="M20.447 20.452h-3.554v-5.569c0-1.328-.027-3.037-1.852-3.037-1.853 0-2.136 1.445-2.136 2.939v5.667H9.351V9h3.414v1.561h.046c.477-.9 1.637-1.85 3.37-1.85 3.601 0 4.267 2.37 4.267 5.455v6.286zM5.337 7.433a2.062 2.062 0 0 1-2.063-2.065 2.064 2.064 0 1 1 2.063 2.065zm1.782 13.019H3.555V9h3.564v11.452zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.222 0h.003z"/>
  </svg>
);

function DeveloperCard() {
  const ref = useReveal(0);

  return (
    <section className="dev-section">
      <div className="dev-grid-bg" />
      <p className="dev-eyebrow">Built by</p>
      <h2 className="dev-heading">Meet the Developer</h2>

      <div ref={ref} className="dev-card reveal">
        {/* Online status */}
        <div className="dev-status-wrap">
          <div className="dev-status-dot" />
          <div className="dev-status-ping" />
        </div>

        {/* Avatar */}
        <div className="dev-avatar-wrap">
          <div className="dev-avatar-ring">
            <img
              src="https://github.com/vamsikoneru06.png"
              alt="Vamsi Koneru"
              className="dev-avatar-img"
              onError={e => { e.target.src = "https://picsum.photos/seed/vamsi-developer/200/200"; }}
            />
          </div>
          <div className="dev-avatar-glow" />
        </div>

        {/* Info */}
        <div className="dev-info">
          <h3 className="dev-name">Vamsi Koneru</h3>
          <p className="dev-role">Full Stack Developer</p>
          <p className="dev-sub">Java · Spring Boot · React</p>
        </div>

        {/* Buttons */}
        <div className="dev-actions">
          <a
            href="https://github.com/vamsikoneru06"
            target="_blank"
            rel="noopener noreferrer"
            className="dev-btn dev-btn-github"
            title="GitHub"
          >
            <IcoGithub />
            <span>GitHub</span>
          </a>
          <a
            href="https://www.linkedin.com/in/vamsi-koneru-0a0661330/"
            target="_blank"
            rel="noopener noreferrer"
            className="dev-btn dev-btn-linkedin"
            title="LinkedIn"
          >
            <IcoLinkedin />
            <span>LinkedIn</span>
          </a>
        </div>

        {/* Hover border glow */}
        <div className="dev-border-glow" />
      </div>
    </section>
  );
}

/* ── Footer ──────────────────────────────────────────────────────────────── */
function Footer() {
  return (
    <footer className="footer">
      <div className="footer-inner">
        <p className="footer-copy">© {new Date().getFullYear()} <strong>NearMe</strong> · Explore What's Near You</p>
      </div>
    </footer>
  );
}

/* ── App ─────────────────────────────────────────────────────────────────── */
function App() {
  const [data,        setData]        = useState([]);
  const [dataType,    setDataType]    = useState("Movies");
  const [userLocation,setUserLocation]= useState("Rajahmundry");
  const [searchText,  setSearchText]  = useState("Rajahmundry");
  const [isLoading,   setIsLoading]   = useState(false);
  const [error,       setError]       = useState(null);
  const [selectedItem,setSelectedItem]= useState(null);
  const [theme,       setTheme]       = useState("dark");

  const endpointRef = useRef("api/movies");
  const labelRef    = useRef("Movies");
  const abortRef    = useRef(null);

  useEffect(() => {
    document.documentElement.className = `${theme}-theme`;
  }, [theme]);

  const fetchData = useCallback(async (endpoint, label) => {
    const loc = userLocation.trim();
    endpointRef.current = endpoint;
    labelRef.current    = label;
    setDataType(label);
    setIsLoading(true);
    setError(null);

    if (abortRef.current) abortRef.current.abort();
    const controller = new AbortController();
    abortRef.current = controller;
    const signal = controller.signal;

    try {
      let url;
      if (MAP_KEY) {
        const placeType = PLACES_QUERY[label] || "place";
        url = `${BACKEND}/api/places?city=${encodeURIComponent(loc)}&type=${encodeURIComponent(placeType)}`;
      } else {
        const isUnifiedLocations = endpoint === "api/locations";
        const params = isUnifiedLocations ? `size=20` : `location=${encodeURIComponent(loc)}&size=20`;
        url = `${BACKEND}/${endpoint}?${params}`;
      }

      const res  = await fetch(url, { signal });
      if (!res.ok) throw new Error(`Server error ${res.status}`);
      const json = await res.json();
      if (json.error) throw new Error(json.error);
      setData(Array.isArray(json) ? json : (json.content || []));
    } catch (err) {
      if (err.name === "AbortError") return; // stale request — ignore silently
      console.error(`[NearMe] fetchData(${label}):`, err.message);
      setError(`Could not load ${label}. Make sure the backend is running.`);
      setData([]);
    } finally {
      setIsLoading(false);
    }
  }, [userLocation]);

  useEffect(() => {
    fetchData(endpointRef.current, labelRef.current);
  }, [userLocation]); // intentional: only re-run when location changes

  const handleSearch = useCallback(() => {
    const loc = searchText.trim();
    if (loc) setUserLocation(loc);
  }, [searchText]);

  const handlePick = useCallback((name) => {
    const cat = CATEGORIES.find(c => c.name === name);
    if (cat) fetchData(cat.endpoint, cat.name);
  }, [fetchData]);

  const cat = CATEGORIES.find(c => c.name === dataType);

  const renderContent = () => {
    if (isLoading) return <div className="loader-wrap"><div className="loader" /></div>;
    if (error) return <div className="feedback-wrap error">{error}</div>;
    if (data.length === 0) return (
      <div className="feedback-wrap">
        No results found for &quot;{userLocation}&quot;. Try another city.
      </div>
    );
    if (dataType === "Locations") {
      return (
        <div className="loc-grid">
          {data.map((item, i) => <LocationCard key={item.id} item={item} index={i} onOpen={setSelectedItem} />)}
        </div>
      );
    }
    return (
      <div className="card-grid">
        {data.map((item, i) => <SummaryCard key={item.id} item={item} cat={cat} index={i} onOpen={setSelectedItem} />)}
      </div>
    );
  };

  return (
    <div className="app">
      <Header theme={theme} onToggle={() => setTheme(t => t === "dark" ? "light" : "dark")}
        active={dataType} onPick={handlePick} />

      <Hero city={userLocation} value={searchText} onChange={setSearchText} onSearch={handleSearch} />

      <main className="main-content">
        <div className="content-inner">
          <div className="section-head">
            <h2>
              <span className="brand-grad">{dataType}</span>
              {" in "}
              <span className="result-city">{userLocation}</span>
            </h2>
          </div>
          <CategoryPills active={dataType} onPick={handlePick} />
          {renderContent()}
        </div>
      </main>

      <DeveloperCard />
      <Footer />

      {selectedItem && (
        <DetailModal item={selectedItem} cat={cat} onClose={() => setSelectedItem(null)} />
      )}
    </div>
  );
}

export default App;
