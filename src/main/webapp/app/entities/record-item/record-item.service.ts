import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecordItem } from 'app/shared/model/record-item.model';

type EntityResponseType = HttpResponse<IRecordItem>;
type EntityArrayResponseType = HttpResponse<IRecordItem[]>;

@Injectable({ providedIn: 'root' })
export class RecordItemService {
    public resourceUrl = SERVER_API_URL + 'api/record-items';

    constructor(private http: HttpClient) {}

    create(recordItem: IRecordItem): Observable<EntityResponseType> {
        return this.http.post<IRecordItem>(this.resourceUrl, recordItem, { observe: 'response' });
    }

    update(recordItem: IRecordItem): Observable<EntityResponseType> {
        return this.http.put<IRecordItem>(this.resourceUrl, recordItem, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRecordItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRecordItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
