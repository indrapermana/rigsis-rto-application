import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUnitTypeItem } from 'app/shared/model/unit-type-item.model';

type EntityResponseType = HttpResponse<IUnitTypeItem>;
type EntityArrayResponseType = HttpResponse<IUnitTypeItem[]>;

@Injectable({ providedIn: 'root' })
export class UnitTypeItemService {
    public resourceUrl = SERVER_API_URL + 'api/unit-type-items';

    constructor(private http: HttpClient) {}

    create(unitTypeItem: IUnitTypeItem): Observable<EntityResponseType> {
        return this.http.post<IUnitTypeItem>(this.resourceUrl, unitTypeItem, { observe: 'response' });
    }

    update(unitTypeItem: IUnitTypeItem): Observable<EntityResponseType> {
        return this.http.put<IUnitTypeItem>(this.resourceUrl, unitTypeItem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUnitTypeItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUnitTypeItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
