/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RtoAppTestModule } from '../../../test.module';
import { RecordTypeComponent } from 'app/entities/record-type/record-type.component';
import { RecordTypeService } from 'app/entities/record-type/record-type.service';
import { RecordType } from 'app/shared/model/record-type.model';

describe('Component Tests', () => {
    describe('RecordType Management Component', () => {
        let comp: RecordTypeComponent;
        let fixture: ComponentFixture<RecordTypeComponent>;
        let service: RecordTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [RecordTypeComponent],
                providers: []
            })
                .overrideTemplate(RecordTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecordTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecordTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RecordType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.recordTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
