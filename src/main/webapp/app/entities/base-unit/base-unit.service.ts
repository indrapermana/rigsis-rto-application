import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBaseUnit } from 'app/shared/model/base-unit.model';

type EntityResponseType = HttpResponse<IBaseUnit>;
type EntityArrayResponseType = HttpResponse<IBaseUnit[]>;

@Injectable({ providedIn: 'root' })
export class BaseUnitService {
    public resourceUrl = SERVER_API_URL + 'api/base-units';

    constructor(private http: HttpClient) {}

    create(baseUnit: IBaseUnit): Observable<EntityResponseType> {
        return this.http.post<IBaseUnit>(this.resourceUrl, baseUnit, { observe: 'response' });
    }

    update(baseUnit: IBaseUnit): Observable<EntityResponseType> {
        return this.http.put<IBaseUnit>(this.resourceUrl, baseUnit, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBaseUnit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBaseUnit[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
