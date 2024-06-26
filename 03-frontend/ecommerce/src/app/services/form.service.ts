import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, of } from 'rxjs';
import { Country } from '../common/country';
import { State } from '../common/state';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class FormService {
  private countriesUrl = environment.wowShopApiUrl + '/countries';
  private statesUrl = environment.wowShopApiUrl + '/states';

  constructor(private httpClient: HttpClient) {}

  getCountries(): Observable<Country[]> {
    return this.httpClient
      .get<GetResponseCountries>(this.countriesUrl)
      .pipe(map((response) => response._embedded.countries));
  }

  getStates(countryCode: string): Observable<State[]> {
    // search url
    const searchStatesUrl = `${this.statesUrl}/search/findByCountryCode?code=${countryCode}`;
    return this.httpClient
      .get<GetResponseStates>(searchStatesUrl)
      .pipe(map((response) => response._embedded.states));
  }

  getCreditCardMonth(startMonth: number): Observable<number[]> {
    let data: number[] = [];

    // build an array for Month dropdown list
    // start at current month and loop until

    for (let month = startMonth; month <= 12; month++) {
      data.push(month);
    }

    return of(data);
  }

  getCreditCardYear(): Observable<number[]> {
    let data: number[] = [];

    // build an array for Year dropdown list
    // start at current year and loop for 10 years

    const startYear: number = new Date().getFullYear();
    const endYear: number = startYear + 10;

    for (let year = startYear; year <= endYear; year++) {
      data.push(year);
    }

    return of(data);
  }
}

interface GetResponseCountries {
  _embedded: {
    countries: Country[];
  };
}

interface GetResponseStates {
  _embedded: {
    states: State[];
  };
}
