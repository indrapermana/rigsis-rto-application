/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RtoAppTestModule } from '../../../test.module';
import { DerivedUnitComponent } from 'app/entities/derived-unit/derived-unit.component';
import { DerivedUnitService } from 'app/entities/derived-unit/derived-unit.service';
import { DerivedUnit } from 'app/shared/model/derived-unit.model';

describe('Component Tests', () => {
    describe('DerivedUnit Management Component', () => {
        let comp: DerivedUnitComponent;
        let fixture: ComponentFixture<DerivedUnitComponent>;
        let service: DerivedUnitService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [DerivedUnitComponent],
                providers: []
            })
                .overrideTemplate(DerivedUnitComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DerivedUnitComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DerivedUnitService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DerivedUnit(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.derivedUnits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
