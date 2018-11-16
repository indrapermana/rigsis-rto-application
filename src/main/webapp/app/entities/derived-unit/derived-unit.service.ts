import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDerivedUnit } from 'app/shared/model/derived-unit.model';

type EntityResponseType = HttpResponse<IDerivedUnit>;
type EntityArrayResponseType = HttpResponse<IDerivedUnit[]>;

@Injectable({ providedIn: 'root' })
export class DerivedUnitService {
    public resourceUrl = SERVER_API_URL + 'api/derived-units';

    constructor(private http: HttpClient) {}

    create(derivedUnit: IDerivedUnit): Observable<EntityResponseType> {
        return this.http.post<IDerivedUnit>(this.resourceUrl, derivedUnit, { observe: 'response' });
    }

    update(derivedUnit: IDerivedUnit): Observable<EntityResponseType> {
        return this.http.put<IDerivedUnit>(this.resourceUrl, derivedUnit, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDerivedUnit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDerivedUnit[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
