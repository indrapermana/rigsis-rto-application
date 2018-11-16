import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUnitType } from 'app/shared/model/unit-type.model';

type EntityResponseType = HttpResponse<IUnitType>;
type EntityArrayResponseType = HttpResponse<IUnitType[]>;

@Injectable({ providedIn: 'root' })
export class UnitTypeService {
    public resourceUrl = SERVER_API_URL + 'api/unit-types';

    constructor(private http: HttpClient) {}

    create(unitType: IUnitType): Observable<EntityResponseType> {
        return this.http.post<IUnitType>(this.resourceUrl, unitType, { observe: 'response' });
    }

    update(unitType: IUnitType): Observable<EntityResponseType> {
        return this.http.put<IUnitType>(this.resourceUrl, unitType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUnitType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUnitType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
