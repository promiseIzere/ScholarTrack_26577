const API_BASE_URL = 'http://localhost:9090/api';

class ApiService {
  async request(endpoint, options = {}) {
    const url = `${API_BASE_URL}${endpoint}`;
    const config = {
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
      ...options,
    };

    try {
      const response = await fetch(url, config);
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      
      // Handle empty responses (like DELETE)
      if (response.status === 204) {
        return null;
      }
      
      return await response.json();
    } catch (error) {
      console.error('API request failed:', error);
      throw error;
    }
  }

  // Student endpoints
  async getStudents() {
    return this.request('/students');
  }

  async getStudent(id) {
    return this.request(`/students/${id}`);
  }

  async createStudent(student) {
    return this.request('/students', {
      method: 'POST',
      body: JSON.stringify(student),
    });
  }

  async updateStudent(id, student) {
    return this.request(`/students/${id}`, {
      method: 'PUT',
      body: JSON.stringify(student),
    });
  }

  async deleteStudent(id) {
    return this.request(`/students/${id}`, {
      method: 'DELETE',
    });
  }

  // Simple student creation for testing
  async createStudentSimple(firstName, lastName, email, parentEmail = '', status = 'ACTIVE') {
    const params = new URLSearchParams({
      firstName,
      lastName,
      email,
      parentEmail,
      status,
    });
    return this.request(`/students/simple?${params}`, {
      method: 'POST',
    });
  }

  // Attendance endpoints
  async markAttendance(studentId, present, date = null, remarks = '') {
    const params = new URLSearchParams({
      present: present.toString(),
      ...(date && { date }),
      ...(remarks && { remarks }),
    });
    return this.request(`/attendance/${studentId}?${params}`, {
      method: 'POST',
    });
  }

  async getAttendance(studentId, startDate, endDate) {
    const params = new URLSearchParams({
      start: startDate,
      end: endDate,
    });
    return this.request(`/attendance/${studentId}?${params}`);
  }

  // Performance endpoints
  async getPerformanceReport(studentId, startDate, endDate) {
    const params = new URLSearchParams({
      start: startDate,
      end: endDate,
    });
    return this.request(`/performance/${studentId}?${params}`);
  }

  // Health check
  async healthCheck() {
    return this.request('/health');
  }

  // Auth endpoints
  async login(username, password) {
    const params = new URLSearchParams({ username, password });
    return this.request(`/auth/login?${params}`, {
      method: 'POST',
    });
  }
}

export default new ApiService();
